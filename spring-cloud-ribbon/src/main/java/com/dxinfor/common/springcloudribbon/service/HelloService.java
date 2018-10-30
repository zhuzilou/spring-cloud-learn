package com.dxinfor.common.springcloudribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author endless
 * @date 2018/9/19
 */
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hello(String name) {
        return restTemplate.getForObject("http://SERVICE-PROVIDER/hello?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi," + name + ", sorry. Error!";
    }
}
