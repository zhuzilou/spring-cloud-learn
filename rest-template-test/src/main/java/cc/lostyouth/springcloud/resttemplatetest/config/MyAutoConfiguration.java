package cc.lostyouth.springcloud.resttemplatetest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration简单版
 * Created by endless on 6/2/2020.
 */
@Slf4j
@Configuration
public class MyAutoConfiguration {
    @Autowired(required = false)
    @MyLoadBalanced
    private final List<RestTemplate> myTemplates = Collections.emptyList();

    @Bean
    public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer() {
        log.info("==== 这个Bean将在容器初始化时创建 ====");
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (RestTemplate tpl : myTemplates) {
                    //创建一个自定义的拦截器实例
                    MyInterceptor mi = new MyInterceptor();
                    //获取RestTemplate原来的拦截器
                    List list = new ArrayList(tpl.getInterceptors());
                    //添加拦截器到集合
                    list.add(mi);
                    //将新的拦截器集合设置到RestTemplate实例
                    tpl.setInterceptors(list);
                }
            }
        };
    }
}
