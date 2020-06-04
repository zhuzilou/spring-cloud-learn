package cc.lostyouth.springcloud.springfeigninvoker.controller;

import cc.lostyouth.springcloud.springfeigninvoker.client.PersonClient;
import cc.lostyouth.springcloud.springfeigninvoker.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by endless on 6/3/2020.
 */
@RestController
@Configuration
public class InvokerController {
    @Autowired
    private PersonClient personClient;

    @RequestMapping(value = "/invokeHello", method = RequestMethod.GET)
    public String invokeHello() {
        return personClient.hello();
    }

    @RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String router() {
        Person p = personClient.getPerson(2);
        return p.getMessage();
    }
}
