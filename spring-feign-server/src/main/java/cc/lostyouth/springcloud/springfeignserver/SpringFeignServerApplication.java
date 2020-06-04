package cc.lostyouth.springcloud.springfeignserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringFeignServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFeignServerApplication.class, args);
	}

}
