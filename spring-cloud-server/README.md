# Eureka
1. Spring Boot工程启动类上添加注解@EnalbeEurekaServer
2. 默认情况下Eureka Server也是一个Eureka Client，通过eureka.client.registerWithEureka:false和FetchRegistry:false表明自己
是一个Eureka Server。[application.yml](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-server/src/main/resources/application.yml)