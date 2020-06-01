package cc.lostyouth.springcloud.healthhandlerprovider.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by endless on 6/1/2020.
 */
@RestController
public class HealthController {
    public static Boolean canVisitDb = false;

    @RequestMapping(value = "/db/{canVisitDb}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String setConnectState(@PathVariable("canVisitDb")Boolean canVisitDb) {
        HealthController.canVisitDb = canVisitDb;
        return "当前数据库是否正常：" + canVisitDb;
    }
}
