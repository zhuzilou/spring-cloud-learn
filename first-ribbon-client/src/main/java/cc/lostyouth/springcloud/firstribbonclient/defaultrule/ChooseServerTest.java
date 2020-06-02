package cc.lostyouth.springcloud.firstribbonclient.defaultrule;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用默认规则RoundRobinRule规则选择服务器
 * Created by endless on 6/2/2020.
 */
public class ChooseServerTest {
    public static void main(String[] args) {
        //创建负载均衡器
        ILoadBalancer lb = new BaseLoadBalancer();
        //添加服务器
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("localhost", 8080));
        servers.add(new Server("localhost", 8081));
        lb.addServers(servers);

        //进行6次服务器选择
        for (int i = 0; i < 6; i++) {
            Server s = lb.chooseServer(null);
            //轮流使用8080和8081
            System.out.println(s);
        }
    }
}
