# 写在最前面
学习技术不求其解，用一时爽一时，出问题时才发现天真与现实的差距。希望这次可以学以致用，加深效果。  
* 有学习时间但没有应用环境，是件郁闷的事。Part1已经是2018年10月的记录了，当时激情四射，真正需要应用时已经是2020年5月了，不知道是该可悲还是可叹。
* 没有学习时间但有应用环境，XX挤一挤总会有的。

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
* spring-boot.version: 2.3.0.RELEASE
* spring-cloud.version: Hoxton.SR5
## 2.1 简单配置
* first-ek--server 服务中心
* first-ek-service-provider 服务提供者
* first-ek-service-invoker 服务调用者

## 2.2 [集群配置](https://github.com/zhuzilou/spring-cloud-learn/tree/master/first-cloud-server)
* first-cloud-server 服务中心
* first-cloud-provider 服务提供者
* first-cloud-invoker 服务调用者

## 2.3.1 [健康检查](https://github.com/zhuzilou/spring-cloud-learn/tree/master/health-handler-server)
* health-handler-server 服务中心
* health-handler-provider 服务提供者
* health-handler-invoker 服务调用者
* 参考资料中的spring-boot-starter-actuator入口为/health，spring-boot使用2.2.5.RELEASE版本后，路径变为/actuator/health。
### 学以致用-20200604
* 由于某种需要修改路径/actuator/health->/health
> [修改配置文件](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/html/production-ready-features.html#production-ready-customizing-management-server-context-path)，这样可能导致/actuator无法访问
```
management.endpoints.web.base-path=/
management.endpoints.web.path-mapping.health=healthcheck
```
> 自定义Controller转发，不确定生产环境下是否影响健康检查。[参考](https://www.cnblogs.com/lu51211314/p/10482099.html)
```java
@RestController
public class HealthController {

    @RequestMapping("/health")
    public void health(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("actuator/health").forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
```


## 2.3.2 [Eureka常用配置](https://github.com/zhuzilou/spring-cloud-learn/tree/master/health-handler-server#232-eureka%E5%B8%B8%E7%94%A8%E9%85%8D%E7%BD%AE)

## 2.4 Ribbon
### 2.4.1 [负载调用的简单实现](https://github.com/zhuzilou/spring-cloud-learn/tree/master/first-ribbon-client)
常规项目通过配置服务器端，借助Ribbon实现负载调用。
### 2.4.2 Spring cloud使用Ribbon
使用@LoadBalanced修饰RestTemplate实现，简单到难以想像。
#### 2.4.2.1 [模拟@LoadBalanced](https://github.com/zhuzilou/spring-cloud-learn/tree/master/rest-template-test)
自定义注解拦截URI请求，了解@LoadBalanced工作流程。

## 2.5 [Spring cloud使用Ribbon](https://github.com/zhuzilou/spring-cloud-learn/tree/master/spring-feign-invoker)
Feign主要用于客户端，默认自带负载均衡功能。通过自定义接口绑定需要访问的服务端（接口版Controller），便于在客户端的Controller中注入并调用服务。