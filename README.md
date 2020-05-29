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

简单的微服务系统图
![简单的微服务系统图](https://github.com/zhuzilou/spring-cloud-learn/blob/master/doc/%E7%AE%80%E5%8D%95%E7%9A%84%E5%BE%AE%E6%9C%8D%E5%8A%A1%E7%B3%BB%E7%BB%9F%E5%9B%BE.png)

# Part2: 疯狂Spring Cloud
参考《疯狂Spring Cloud微服务架构实战》

## 简单配置
* spring-cloud-server 服务中心
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