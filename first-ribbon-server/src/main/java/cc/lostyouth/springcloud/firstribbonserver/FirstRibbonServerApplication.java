package cc.lostyouth.springcloud.firstribbonserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Scanner;

@SpringBootApplication
public class FirstRibbonServerApplication {

    public static void main(String[] args) {
//		SpringApplication.run(FirstRibbonServerApplication.class, args);
        Scanner scan = new Scanner(System.in);
        String port = scan.nextLine();
        new SpringApplicationBuilder(FirstRibbonServerApplication.class).properties("server.port=" + port).run(args);
    }
}
