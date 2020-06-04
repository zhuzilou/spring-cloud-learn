package cc.lostyouth.springcloud.springfeignprovider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Scanner;

@EnableEurekaClient
@SpringBootApplication
public class SpringFeignProviderApplication {

    public static void main(String[] args) {
//		SpringApplication.run(SpringFeignProviderApplication.class, args);
        Scanner scan = new Scanner(System.in);
        String port = scan.nextLine();
        new SpringApplicationBuilder(SpringFeignProviderApplication.class)
                .properties("server.port=" + port).run(args);
    }

}
