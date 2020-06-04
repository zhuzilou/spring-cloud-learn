package cc.lostyouth.springcloud.springfeignprovider.controller;

import cc.lostyouth.springcloud.springfeignprovider.model.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by endless on 5/28/2020.
 */
@RestController
public class FirstController {
    @RequestMapping(value = "/person/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findPerson(@PathVariable("personId") Integer personId, HttpServletRequest request) {
        Person person = Person.builder()
                .id(personId)
                .name("CrazyIt")
                .age(30)
                .build();
        person.setMessage(request.getRequestURL().toString());
        return person;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(HttpServletRequest request) {
        return "Hello world " + request.getRequestURL().toString();
    }
}
