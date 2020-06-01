# Part1: spring-cloud
[源文链接--作者方志朋](https://blog.csdn.net/forezp/article/details/70148833/)

项目基于Spring Boot 2.0.5.RELEASE，Spring Cloud Finchley.RELEASE，项目包含：
* spring-cloud-server(服务注册中心)
* spring-cloud-service-provider(服务提供者)
* spring-cloud-ribbon(服务消费者Ribbon方式)
* spring-cloud-feign(服务消费者Feign方式)
* spring-cloud-zuul(服务路由和过滤)

## [服务注册中心 Eureka Server](https://github.com/zhuzilou/spring-cloud-learn/tree/master/spring-cloud-server)

## [服务提供者 Eureka Client](https://github.com/zhuzilou/spring-cloud-learn/tree/master/spring-cloud-service-provider)

## 服务消费者
在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于http restful的。Spring Cloud有两种服务调用方式，
一种是Ribbon + restTemplate，另一种是Feign。
### [Ribbon方式](https://github.com/zhuzilou/spring-cloud-learn/tree/master/spring-cloud-ribbon)

### [Feign方式](https://github.com/zhuzilou/spring-cloud-learn/tree/master/spring-cloud-feign)

![Ribbon方式系统图，Feign类似](https://github.com/zhuzilou/spring-cloud-learn/blob/master/doc/Ribbon%E7%89%88%E7%B3%BB%E7%BB%9F%E5%9B%BE.png)

## 断路器（Hystrix）
当对特定的服务的调用的不可用达到一个阀值（Hystrix是5秒20次）断路器将会被打开，断路器打开后，可用避免连锁故障，fallback
方法可以直接返回一个固定值。

### Ribbon
1. 在启动类上添加@EnableHystrix注解开启Hystrix。
2. 在Service中消费服务提供者提供的接口方法上，添加@HystrixCommand注解，并指定fallbackMethod熔断方法。当此接口提供的服务不可
用时，会执行快速失败，而不是等待响应超时，这很好的控制了容器的线程阻塞。[Example](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-ribbon/src/main/java/com/dxinfor/common/springcloudribbon/service/HelloService.java)

### Feign
1. Feign是自带断路器的，在配置文件中添加feign.hystrix.enabled=true打开。
2. 在Feign接口类中，添加fallback到@FeignClient注解中，指定对应的实现类，实现类中的同名方法即为失败后执行的方法。[Example](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-feign/src/main/java/com/dxinfor/common/springcloudfeign/service/ScheduleServiceHiHystrix.java)

## 路由网关（zuul） 
[Example](https://github.com/zhuzilou/spring-cloud-learn/tree/master/spring-cloud-zuul)

### 简单的微服务系统图
![简单的微服务系统图](https://github.com/zhuzilou/spring-cloud-learn/blob/master/doc/%E7%AE%80%E5%8D%95%E7%9A%84%E5%BE%AE%E6%9C%8D%E5%8A%A1%E7%B3%BB%E7%BB%9F%E5%9B%BE.png)

# Part2: 疯狂Spring Cloud
参考《疯狂Spring Cloud微服务架构实战》

## 简单配置
* first-ek--server 服务中心
* first-ek-service-provider 服务提供者
* first-ek-service-invoker 服务调用者

## 集群配置
* 配置运行环境的hosts：127.0.0.1 slave1 slave2
* first-cloud-server 服务中心 http://slave1:8761, http://slave:8762
>通过启动后输入不同配置启动两个服务注册中心，首先启动的服务控制台会报异常，是由于无法找到另一个注册中心造成，第二个启动后两者正常。
>
>这里建议在yml文件中添加server.enable-self-preservation=false配置，清除启动异常或不再使用的服务。该配置是注册中心的自我保护机制，
>防止已关闭的实例无法从注册中心剔除，服务关闭后up依然显示，导致后续测试调用服务时分配到异常服务端，返回异常。
* first-cloud-provider 服务提供者 http://localhost:8081, http://localhost:8082
>通过启动后输入不同端口启动两个服务提供者，启动成功后在服务注册中心的Status中可以查看多个实例。
* first-cloud-invoker 服务调用者 http://localhost:9000
>启动后创建Test模拟多次调用http://localhost:9000 ，查看返回结果发现会分别调用8081和8082服务。
### 服务注册中心打开自我保护机制
![服务注册中心](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/2.%20%E6%9C%8D%E5%8A%A1%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83-%E6%89%93%E5%BC%80%E8%87%AA%E6%88%91%E4%BF%9D%E6%8A%A4%E6%9C%BA%E5%88%B6.png)
#### 测试结果
![测试结果](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/2.%20%E8%B0%83%E7%94%A8%E6%9C%8D%E5%8A%A1%E7%BB%93%E6%9E%9C-%E9%83%A8%E5%88%86%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E8%80%85%E5%BC%82%E5%B8%B8.png)
### 服务注册中心关闭自我保护机制
![服务注册中心](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/1.%20%E6%9C%8D%E5%8A%A1%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83-%E5%85%B3%E9%97%AD%E8%87%AA%E6%88%91%E4%BF%9D%E6%8A%A4%E6%9C%BA%E5%88%B6.png)
#### 测试结果
![测试结果](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/1.%20%E8%B0%83%E7%94%A8%E6%9C%8D%E5%8A%A1%E7%BB%93%E6%9E%9C-%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E8%80%85%E6%AD%A3%E5%B8%B8.png)
### [什么是DS Replicas](https://blog.csdn.net/u012817635/article/details/80189579)

## 健康检查
* health-handler-server 服务中心
* health-handler-provider 服务提供者
* health-handler-invoker 服务调用者
### 默认健康检查入口
添加spring-boot-starter-actuator依赖后，[访问页面](http://localhost:8762/actuator/health)，注意与1.5.3地址不同。
### 自定义健康检查-服务提供者
1. [MyHealthIndicator](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-provider/src/main/java/cc/lostyouth/springcloud/healthhandlerprovider/config/MyHealthIndicator.java)
实现org.springframework.boot.actuate.health.HealthIndicator.health()，自定义判断返回up或down。
2. [MyHealthCheckHandler](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-provider/src/main/java/cc/lostyouth/springcloud/healthhandlerprovider/config/MyHealthCheckHandler.java)
重写com.netflix.appinfo.HealthCheckHandler.getStatus()，通过判断MyHealthIndicator.health结果，
返回InstanceInfo.InstanceStatus.UP或DOWN告诉服务器当前健康状态。
3. 配置文件中添加instance-info-replication-interval-seconds设置检查时间，默认30秒。
#### 健康检查
![服务器健康检查失败](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-provider/src/main/resources/%E6%9C%8D%E5%8A%A1%E5%99%A8down.png)
![每10秒检查一次](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-provider/src/main/resources/%E6%AF%8F10%E7%A7%92%E6%A3%80%E6%9F%A5%E6%9C%8D%E5%8A%A1%E7%8A%B6%E6%80%81.png)
### 获取服务状态-服务调用者
* 注入org.springframework.cloud.client.discovery.DiscoveryClient，通过getServices()获取服务列表ID，通过getInstances(id)获取服务实例。
服务实例包含状态、服务名等信息。
* ![服务器状态信息](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-invoker/src/main/resources/%E6%9C%8D%E5%8A%A1%E7%8A%B6%E6%80%81%E4%BF%A1%E6%81%AF.png)

## Eureka常用配置
### 心跳检测
* eureka.instance.lease-renewal-interval-in-seconds
客户端实例向服务器发送周期性的心跳，默认30秒发送一次
* eureka.instance.lease-expiration-duration-in-seconds
服务器接收心跳请求期限，默认90秒，一定期限内未收到客户端实例的心跳，该实例将从注册表中清理掉。
* eureka.server.eviction-interval-timer-in-ms
服务器端清理注册表定时器，默认60秒
* eureka.server.enable-self-preservation
若服务器打开自我保护模式，则客户端实例不会被清理。
### 客户端获取注册表间隔
eureka.client.registry-fetch-interval-seconds
默认情况下，客户端每隔30秒去服务器端抓取注册表（可用服务列表），并将服务器端的注册表保存到本地缓存中。
### 配置与使用元数据
eureka.instance.metadata-map
自定义属性指定元数据，可提供给其他客户端使用，元数据会保存在服务器的注册表中。
通过DiscoveryClient.getInstances(服务名)获取实例列表，实例.getMetadata()获取参数集合。
#### provider中配置元数据，invoker中获取元数据
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
![获取元数据](https://github.com/zhuzilou/spring-cloud-learn/blob/master/health-handler-invoker/src/main/resources/%E8%8E%B7%E5%8F%96%E5%85%83%E6%95%B0%E6%8D%AE.png)

