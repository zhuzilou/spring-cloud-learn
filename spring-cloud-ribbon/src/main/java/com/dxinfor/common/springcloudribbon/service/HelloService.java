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

    /**
     * 通过注入IOC容器的restTemplate来消费service-provider（服务提供者）的hello接口。
     *
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "hiError")
    public String hello(String name) {
        /*
        使用程序名替代了具体的url地址，在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用
        具体的url替换掉服务名。
        */
        return restTemplate.getForObject("http://SERVICE-PROVIDER/hello?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi," + name + ", sorry. Error!";
    }
}
