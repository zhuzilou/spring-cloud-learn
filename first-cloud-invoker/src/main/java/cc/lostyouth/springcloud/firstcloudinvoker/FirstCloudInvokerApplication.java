package cc.lostyouth.springcloud.firstcloudinvoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FirstCloudInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstCloudInvokerApplication.class, args);
    }

}
