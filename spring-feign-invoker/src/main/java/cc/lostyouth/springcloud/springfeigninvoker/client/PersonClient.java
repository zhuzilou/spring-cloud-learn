package cc.lostyouth.springcloud.springfeigninvoker.client;

import cc.lostyouth.springcloud.springfeigninvoker.model.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by endless on 6/3/2020.
 */
@FeignClient("spring-feign-provider")
public interface PersonClient {
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String hello();

    @RequestMapping(method = RequestMethod.GET, value = "/person/{personId}")
    Person getPerson(@PathVariable("personId")Integer personId);
}
