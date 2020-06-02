# 初识Ribbon
* first-ribbon-server 服务器
* first-ribbon-client 客户端

看的比较晕，只能先贴代码了。Ribbon主要配置在客户端，在服务器列表已知的情况下，对方若没有前置负载（一般应该不会有这种情况出现），通过配置客户端实现负载的调用。
此方法与实际前置负载IP有多少区别，暂时未知。

## 默认负载规则
Ribbon默认使用RoundRobinRule负载规则调用服务器端
### 代码配置
```java
ConfigurationManager.getConfigInstance().setProperty(
        "my-client.ribbon.listOfServers",
        "localhost:8080,localhost:8081"
);
```
### 配置文件
```properties
# <client>.<nameSpace>.<property>=<value>
# client: 客户端名称，使用ClientFactory时传入该名称，返回对应客户端实例。
# nameSpace: 该配置的命名空间，默认为ribbon
# property: 属性名
my-client.ribbon.listOfServers=localhost:8080,localhost:8081
```

### 请求服务器
```java
//设置请求的服务器
//1. 手动添加
//        ConfigurationManager.getConfigInstance().setProperty(
//                "my-client.ribbon.listOfServers",
//                "localhost:8080,localhost:8081"
//        );
//2. 从配置文件读取
ConfigurationManager.loadPropertiesFromResources("application.properties");

//获取REST客户端
AbstractLoadBalancerAwareClient<HttpRequest, HttpResponse> client = (AbstractLoadBalancerAwareClient) ClientFactory.getNamedClient("my-client");
//创建请求实例
HttpRequest request = HttpRequest.newBuilder().uri("/person/1").build();

//发送6次请求到服务器中
for (int i = 0; i < 6; i++) {
    HttpResponse response = client.executeWithLoadBalancer(request);
    String result = response.getEntity(String.class);
    System.out.println(result);
}
```

## 自定义负载规则
自定义实现IRule，通过choose方法按自定义规则返回Server，实际需要考虑具体业务的发生时间、服务器性能等。
```java
public class MyRule implements IRule {
    ILoadBalancer lb;
    public MyRule() {}

    public MyRule(ILoadBalancer lb) {
        this.lb = lb;
    }
    @Override
    public Server choose(Object key) {
        //获取全部服务器
        List<Server> servers = lb.getAllServers();
        //只返回第一个Server对象
        return servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
```
客户端调用时配置规则处理类
```java
//配置规则处理类，如果配置错误，则仍使用默认规则RoundRobinRule
ConfigurationManager.getConfigInstance().setProperty(
        "my-client.ribbon.NFLoadBalancerRuleClassName",
        MyRule.class.getName()
);
```

## Ribbon负载规则
### RoundRobinRule
系统默认的规则，通过简单地轮询服务列表来选择服务器，其他规则在很多情况下仍然使用RoundRobinRule。
### vailabilityFilteringRule
该规则会忽略以下服务器。
* 无法连接的服务器：在默认情况下，如果3次连接失败，该服务器将会被置为“短路”的状态，该状态将持续30秒；如果再次连接失败，“短路”状态的持续时间将会以几何级数增加。可以通过修改niws.loadbalancer.\<clientName\>.connectionFailureCountThreshold属性，来配置连接失败的次数。
* 并发数过高的服务器：如果连接到该服务器的并发数过高，也会被这个规则忽略，可以通过修改\<clientName\>.ribbon.ActiveConnectionsLirnit属性来设定最高并发数。
### WeightedResponseTimeRule
为每个服务器赋予一个权重值，服务器的响应时间越长，该权重值就越少，这个规则会随机选择服务器，权重值有可能会决定服务器的选择。
### ZoneAvoidanceRule
该规则以区域、可用服务器为基础进行服务器选择。使用Zone对服务器进行分类，可以理解为机架或者机房。
### BestAvailableRule
忽略“短路”的服务器，并选择并发数较低的服务器。
### RandomRule
顾名思义，随机选择可用的服务器。
### Re问rRule
含有重试的选择逻辑，如果使用RoundRobinRule选择的服务器无法连接，那么将会重新选择服务器。

