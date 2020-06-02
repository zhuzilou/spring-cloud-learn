package cc.lostyouth.springcloud.firstribbonclient.customerrule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 自定义规则示例，实际需要考虑具体业务的发生时间、服务器性能等。
 * Created by endless on 6/2/2020.
 */
public class MyRule implements IRule {
    ILoadBalancer lb;
    public MyRule() {}

    public MyRule(ILoadBalancer lb) {
        this.lb = lb;
    }
    @Override
    public Server choose(Object key) {
        //获取全部服务器
        List<Server> servers = lb.getAllServers();
        //只返回第一个Server对象
        return servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
