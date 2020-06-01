package cc.lostyouth.springcloud.healthhandlerinvoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HealthHandlerInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthHandlerInvokerApplication.class, args);
    }

}
