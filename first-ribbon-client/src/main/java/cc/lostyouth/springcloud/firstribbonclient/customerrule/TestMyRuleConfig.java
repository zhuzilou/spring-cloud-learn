package cc.lostyouth.springcloud.firstribbonclient.customerrule;

import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;

/**
 * Created by endless on 6/2/2020.
 */
public class TestMyRuleConfig {
    public static void main(String[] args) throws Exception {
        //设置请求的服务器
        ConfigurationManager.getConfigInstance().setProperty(
                "my-client.ribbon.listOfServers",
                "localhost:8080,localhost:8081"
        );
        //配置规则处理类，如果配置错误，则仍使用默认规则RoundRobinRule
        ConfigurationManager.getConfigInstance().setProperty(
                "my-client.ribbon.NFLoadBalancerRuleClassName",
                MyRule.class.getName()
        );

        //获取REST客户端
        AbstractLoadBalancerAwareClient<HttpRequest, HttpResponse> client = (AbstractLoadBalancerAwareClient) ClientFactory.getNamedClient("my-client");
        //创建请求实例
        HttpRequest request = HttpRequest.newBuilder().uri("/person/1").build();

        //发送6次请求到服务器中
        for (int i = 0; i < 6; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request);
            String result = response.getEntity(String.class);
            System.out.println(result);
        }
    }
}
