package cc.lostyouth.springcloud.firstcloudprovider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Scanner;

@EnableEurekaClient
@SpringBootApplication
public class FirstCloudProviderApplication {

    public static void main(String[] args) {
//		SpringApplication.run(FirstCloudProviderApplication.class, args);
        Scanner scan = new Scanner(System.in);
        String port = scan.nextLine();
        new SpringApplicationBuilder(FirstCloudProviderApplication.class)
                .properties("server.port=" + port).run(args);
    }
}
