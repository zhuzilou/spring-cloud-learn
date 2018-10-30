package com.dxinfor.common.springcloudfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author endless
 * @date 2018/10/15
 */
@FeignClient(value = "service-provider", fallback = ScheduleServiceHiHystrix.class)
public interface ScheduleServiceHi {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
