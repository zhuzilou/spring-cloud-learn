package cc.lostyouth.springcloud.healthhandlerprovider.config;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * Created by endless on 6/1/2020.
 */
@Slf4j
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {
    @Autowired
    private MyHealthIndicator indicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
        Status s = indicator.getHealth(true).getStatus();
        if (s.equals(Status.UP)) {
            log.info("数据库正常连接");
            return InstanceInfo.InstanceStatus.UP;
        } else {
            log.error("数据库无法连接");
            return InstanceInfo.InstanceStatus.DOWN;
        }
    }
}
