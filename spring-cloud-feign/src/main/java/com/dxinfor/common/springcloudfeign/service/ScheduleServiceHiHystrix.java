package com.dxinfor.common.springcloudfeign.service;

import org.springframework.stereotype.Component;

/**
 *
 * @author endless
 * @date 2018/10/15
 */
@Component
public class ScheduleServiceHiHystrix implements ScheduleServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry, " + name;
    }
}
