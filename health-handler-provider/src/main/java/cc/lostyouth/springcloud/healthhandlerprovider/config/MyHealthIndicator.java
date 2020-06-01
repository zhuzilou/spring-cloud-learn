package cc.lostyouth.springcloud.healthhandlerprovider.config;

import cc.lostyouth.springcloud.healthhandlerprovider.controller.HealthController;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by endless on 6/1/2020.
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if(HealthController.canVisitDb){
            return Health.up().build();
        } else {
            return Health.down().build();
        }
    }
}
