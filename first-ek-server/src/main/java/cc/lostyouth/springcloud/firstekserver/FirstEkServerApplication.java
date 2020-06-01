package cc.lostyouth.springcloud.firstekserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class FirstEkServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstEkServerApplication.class, args);
	}

}
