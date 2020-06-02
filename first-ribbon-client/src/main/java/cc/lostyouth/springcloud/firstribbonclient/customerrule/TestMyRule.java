package cc.lostyouth.springcloud.firstribbonclient.customerrule;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by endless on 6/2/2020.
 */
public class TestMyRule {
    public static void main(String[] args) {
        //创建负载均衡器
        BaseLoadBalancer lb = new BaseLoadBalancer();
        //使用自定义负载规则
        lb.setRule(new MyRule(lb));
        //添加服务器
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("localhost", 8080));
        servers.add(new Server("localhost", 8081));
        lb.addServers(servers);

        //进行6次服务器选择
        for (int i = 0; i < 6; i++) {
            Server s = lb.chooseServer(null);
            //自定义规则只使用8080服务器
            System.out.println(s);
        }
    }
}
