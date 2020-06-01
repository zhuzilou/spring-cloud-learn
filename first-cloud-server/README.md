# 2.2 集群配置
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

## 服务注册中心打开自我保护机制
![服务注册中心](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/2.%20%E6%9C%8D%E5%8A%A1%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83-%E6%89%93%E5%BC%80%E8%87%AA%E6%88%91%E4%BF%9D%E6%8A%A4%E6%9C%BA%E5%88%B6.png)

### 测试结果
![测试结果](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/2.%20%E8%B0%83%E7%94%A8%E6%9C%8D%E5%8A%A1%E7%BB%93%E6%9E%9C-%E9%83%A8%E5%88%86%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E8%80%85%E5%BC%82%E5%B8%B8.png)

## 服务注册中心关闭自我保护机制
![服务注册中心](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/1.%20%E6%9C%8D%E5%8A%A1%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83-%E5%85%B3%E9%97%AD%E8%87%AA%E6%88%91%E4%BF%9D%E6%8A%A4%E6%9C%BA%E5%88%B6.png)

### 测试结果
![测试结果](https://github.com/zhuzilou/spring-cloud-learn/blob/master/first-cloud-server/src/main/resources/1.%20%E8%B0%83%E7%94%A8%E6%9C%8D%E5%8A%A1%E7%BB%93%E6%9E%9C-%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E8%80%85%E6%AD%A3%E5%B8%B8.png)

## [什么是DS Replicas](https://blog.csdn.net/u012817635/article/details/80189579)