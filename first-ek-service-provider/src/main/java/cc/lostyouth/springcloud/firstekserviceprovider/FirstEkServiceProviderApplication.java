package cc.lostyouth.springcloud.firstekserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FirstEkServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstEkServiceProviderApplication.class, args);
	}

}
