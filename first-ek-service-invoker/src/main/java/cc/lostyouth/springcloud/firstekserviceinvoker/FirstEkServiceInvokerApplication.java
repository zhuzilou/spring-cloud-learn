package cc.lostyouth.springcloud.firstekserviceinvoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FirstEkServiceInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstEkServiceInvokerApplication.class, args);
    }

}
