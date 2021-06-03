package cc.lostyouth.springcloud.firstcloudserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Scanner;

@EnableEurekaServer
@SpringBootApplication
public class FirstCloudServerApplication {

    public static void main(String[] args) {
		SpringApplication.run(FirstCloudServerApplication.class, args);
//        Scanner scan = new Scanner(System.in);
//        String profiles = scan.nextLine();
//        new SpringApplicationBuilder(FirstCloudServerApplication.class)
//                .profiles(profiles).run(args);
    }

}
