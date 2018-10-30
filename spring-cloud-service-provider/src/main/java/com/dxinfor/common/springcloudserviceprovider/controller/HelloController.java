package com.dxinfor.common.springcloudserviceprovider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author endless
 * @date 2018/9/19
 */
@RestController
public class HelloController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/hello")
    public String home(@RequestParam(value = "name", defaultValue = "dxinfor") String name) {
        return "Hello " + name + ", I am from port: " + port;
    }
}
