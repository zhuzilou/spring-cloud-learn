# Eureka Client
当Client向Server注册时，它会提供一些元数据，例如主机和端口，URL，主页等。Eureka Server从每个Client实例接收心跳
消息，如果心跳超时，则通常将该实例从注册Server中删除。

1. 通过使用注解@EnableEurekaClient表明自己是一个Client
2. 在配置文件中注明服务注册中心的地址 [application.yml](https://github.com/zhuzilou/spring-cloud-learn/blob/master/spring-cloud-service-provider/src/main/resources/application.yml)