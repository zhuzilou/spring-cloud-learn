package cc.lostyouth.springcloud.healthhandlerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HealthHandlerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthHandlerServerApplication.class, args);
	}

}
