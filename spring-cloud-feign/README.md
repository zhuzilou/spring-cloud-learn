# 服务消费者（Feign方式）
Feign默认集成了Ribbon，即默认默认实现了负载均衡的效果
1. 通过@EnableFeignClients注解开启Feign功能，并通过@EnableDiscoveryClient向服务中心注册。
2. 配置Ribbon程序名和指定服务的注册中心地址。[application.yml](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-feign/src/main/resources/application.yml)
3. 定义一个feign接口，通过@FeignClient("服务名")，来指定调用哪个服务。[FeignClient](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-feign/src/main/java/com/dxinfor/common/springcloudfeign/service/ScheduleServiceHi.java)
4. 添加Controller，对外暴露接口，通过刚才定义的Feign客户端来消费服务。[Controller](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-feign/src/main/java/com/dxinfor/common/springcloudfeign/controller/HiController.java)