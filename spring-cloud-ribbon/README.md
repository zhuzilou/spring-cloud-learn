# 服务消费者（Ribbon方式）
1. 通过@EnableDiscoveryClient向服务中心注册。
2. 向程序的ioc注入一个bean: RestTemplate，并通过@LoadBalanced注解表明这个restTemplate开启负载均衡的功能。[Example](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-ribbon/src/main/java/com/dxinfor/common/springcloudribbon/SpringCloudRibbonApplication.java)
3. 配置Ribbon程序名和指定服务的注册中心地址。[application.yml](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-ribbon/src/main/resources/application.yml)
4. 添加Service类消费服务提供者提供的接口。[HelloService](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-ribbon/src/main/java/com/dxinfor/common/springcloudribbon/service/HelloService.java)
5. 添加Controller调用Service，暴露此服务接口。[HelloController](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-ribbon/src/main/java/com/dxinfor/common/springcloudribbon/controller/HelloController.java)