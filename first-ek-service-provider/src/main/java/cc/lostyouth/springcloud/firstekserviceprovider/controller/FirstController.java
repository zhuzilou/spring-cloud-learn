package cc.lostyouth.springcloud.firstekserviceprovider.controller;

import cc.lostyouth.springcloud.firstekserviceprovider.model.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by endless on 5/28/2020.
 */
@RestController
public class FirstController {
    @RequestMapping(value = "/person/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findPerson(@PathVariable("personId")Integer personId) {
        return new Person(personId, "Crazyit", 30);
    }
}
