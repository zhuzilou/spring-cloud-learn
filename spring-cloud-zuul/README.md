# Zuul
Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，比如/api/user转发到到user服务，/api/shop转发到到shop服务。
zuul默认和Ribbon结合实现了负载均衡的功能。
1. 在启动类添加@EnableZuulProxy，开启Zuul。
2. 配置服务名及Zuul路由，以/api-a/开头的请求都转发给service-ribbon，以/api-b/开头的请求都转发给service-feign。
[application.yml](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-zuul/src/main/resources/application.yml)

## 服务过滤
通过自定义Filter（继承ZuulFilter）对服务进行过滤。[MyFilter](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-zuul/src/main/java/com/dxinfor/common/springcloudzuul/MyFilter.java)

继承ZuulFilter需要重写四个方法：
* filterType: 返回一个字符串代表过滤类型（Zuul的四种不同生命周期）。
    * pre: 路由之前
    * routing: 路由之时
    * post: 路由之后
    * error: 发送错误调用

* filterOrder: 过滤的顺序
* shouldFilter: 这里可以写逻辑判断，是否需要过滤，true表示永远过滤。
* run: 过滤器的具体逻辑。可用很复杂，包括查sql等去判断是否有权限访问。