package cc.lostyouth.springcloud.firsthystrixserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by endless on 6/5/2020.
 */
@RestController
public class MyController {
    @GetMapping("/normalHello")
    public String normalHello(HttpServletRequest request) {
        return "Hello world";
    }

    @GetMapping("/errorHello")
    public String errorHello(HttpServletRequest request) throws InterruptedException {
        //模拟需要处理10秒
        Thread.sleep(10000);
        return "Error hello world";
    }
}
