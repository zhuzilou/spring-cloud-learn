package cc.lostyouth.springcloud.resttemplatetest.controller;

import cc.lostyouth.springcloud.resttemplatetest.config.MyLoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by endless on 6/2/2020.
 */
@RestController
@Configuration
public class InvokerController {
    @Bean
    @MyLoadBalanced
    public RestTemplate getMyRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 浏览器访问的请求
     * @return
     */
    @RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String router() {
        RestTemplate restTemplate = getMyRestTemplate();
        String json = restTemplate.getForObject("http://my-server/hello", String.class);
        return json;
    }

    /**
     * 最终请求都会转到这个服务
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello world";
    }
}
