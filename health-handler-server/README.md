# 2.3.1 健康检查
* health-handler-server 服务中心
* health-handler-provider 服务提供者
* health-handler-invoker 服务调用者

## 默认健康检查入口
添加spring-boot-starter-actuator依赖后，[访问页面](http://localhost:8762/actuator/health)，注意与1.5.3地址不同。

## 自定义健康检查-服务提供者
1. 实现org.springframework.boot.actuate.health.HealthIndicator.health()，自定义判断返回up或down。
```java
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        //HealthController.canVisitDb自定义判断方法
        if(HealthController.canVisitDb){
            return Health.up().build();
        } else {
            return Health.down().build();
        }
    }
}
```
2. 重写com.netflix.appinfo.HealthCheckHandler.getStatus()，通过判断MyHealthIndicator.health结果，返回InstanceInfo.InstanceStatus.UP或DOWN告诉服务器当前健康状态。
```java
@Slf4j
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {
    @Autowired
    private MyHealthIndicator indicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
        Status s = indicator.getHealth(true).getStatus();
        if (s.equals(Status.UP)) {
            log.info("数据库正常连接");
            return InstanceInfo.InstanceStatus.UP;
        } else {
            log.error("数据库无法连接");
            return InstanceInfo.InstanceStatus.DOWN;
        }
    }
}
```
3. 配置文件中添加instance-info-replication-interval-seconds设置检查时间，默认30秒。
* ![服务器健康检查失败](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-provider/src/main/resources/%E6%9C%8D%E5%8A%A1%E5%99%A8down.png)
* ![每10秒检查一次](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-provider/src/main/resources/%E6%AF%8F10%E7%A7%92%E6%A3%80%E6%9F%A5%E6%9C%8D%E5%8A%A1%E7%8A%B6%E6%80%81.png)

## 获取服务状态-服务调用者
* 注入org.springframework.cloud.client.discovery.DiscoveryClient，通过getServices()获取服务列表ID，通过getInstances(id)获取服务实例。
服务实例包含状态、服务名等信息。
* ![服务器状态信息](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-invoker/src/main/resources/%E6%9C%8D%E5%8A%A1%E7%8A%B6%E6%80%81%E4%BF%A1%E6%81%AF.png)

# 2.3.2 Eureka常用配置

## 心跳检测
* eureka.instance.lease-renewal-interval-in-seconds
客户端实例向服务器发送周期性的心跳，默认30秒发送一次
* eureka.instance.lease-expiration-duration-in-seconds
服务器接收心跳请求期限，默认90秒，一定期限内未收到客户端实例的心跳，该实例将从注册表中清理掉。
* eureka.server.eviction-interval-timer-in-ms
服务器端清理注册表定时器，默认60秒
* eureka.server.enable-self-preservation
若服务器打开自我保护模式，则客户端实例不会被清理。

## 客户端获取注册表间隔
eureka.client.registry-fetch-interval-seconds
默认情况下，客户端每隔30秒去服务器端抓取注册表（可用服务列表），并将服务器端的注册表保存到本地缓存中。

## 配置与使用元数据
eureka.instance.metadata-map
自定义属性指定元数据，可提供给其他客户端使用，元数据会保存在服务器的注册表中。
通过DiscoveryClient.getInstances(服务名)获取实例列表，实例.getMetadata()获取参数集合。

### provider中配置元数据，invoker中获取元数据
* 配置元数据：
```yaml
eureka:
    instance:
    # 自定义元数据
      metadata-map:
        company-name: lostyouth
```
* 获取元数据：
```java
@RequestMapping(value = "/metadata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, String> getMetaData() {
    List<ServiceInstance> ins = discoveryClient.getInstances("health-handler-provider");
    return ins.iterator().next().getMetadata();
}
```
* ![获取元数据](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-invoker/src/main/resources/%E8%8E%B7%E5%8F%96%E5%85%83%E6%95%B0%E6%8D%AE.png)
