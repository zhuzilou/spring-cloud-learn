package com.dxinfor.common.springcloudfeign.controller;

import com.dxinfor.common.springcloudfeign.service.ScheduleServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by endless on 2018/10/15.
 */
@RestController
public class HiController {
    @Autowired
    ScheduleServiceHi scheduleServiceHi;

    @GetMapping(value = "/hello")
    public String sayHi(@RequestParam String name) {
        return scheduleServiceHi.sayHiFromClientOne(name);
    }
}
