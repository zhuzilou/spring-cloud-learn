package com.dxinfor.common.springcloudserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringCloudServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServiceProviderApplication.class, args);
    }
}
