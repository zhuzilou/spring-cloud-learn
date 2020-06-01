package cc.lostyouth.springcloud.healthhandlerinvoker.controller;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by endless on 5/29/2020.
 */
@Slf4j
@RestController
@Configuration
public class InvokeController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> router() {
        Map<String, String> result = new HashMap<>();
        //查询服务列表
        List<ServiceInstance> ins = getServiceInstances();
        //输出服务信息状态
        for (ServiceInstance service : ins) {
            EurekaServiceInstance esi = (EurekaServiceInstance) service;
            InstanceInfo info = esi.getInstanceInfo();
            result.put(info.getInstanceId(), info.getAppName() + "---" + info.getInstanceId() + "---" + info.getStatus());
        }
        return result;
    }

    /**
     * 查询可用服务
     *
     * @return
     */
    public List<ServiceInstance> getServiceInstances() {
        List<String> ids = discoveryClient.getServices();
        List<ServiceInstance> result = new ArrayList<>();
        for (String id : ids) {
            List<ServiceInstance> ins = discoveryClient.getInstances(id);
            result.addAll(ins);
        }
        return result;
    }
}
