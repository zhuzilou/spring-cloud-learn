package cc.lostyouth.springcloud.firsthystrixclient.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by endless on 6/5/2020.
 */
@Slf4j
public class HelloCommand extends HystrixCommand<String> {
    CloseableHttpClient httpClient;
    private String url;

    public HelloCommand(String url) {
        //调用父类的构造器，设置命令组的key，默认用来作为线程池的key
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        //创建HttpClient客户端
        this.httpClient = HttpClients.createDefault();
        this.url = url;
    }

    @Override
    protected String run() throws Exception {
        try {
            //调用GET方法请求服务
            HttpGet httpGet = new HttpGet(url);
            //得到服务响应
            HttpResponse response = httpClient.execute(httpGet);
            //解析并返回命令执行结果
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 默认情况下，调用的Web服务无法在1秒内完成，将触发回退。
     * @return
     */
    @Override
    protected String getFallback() {
        log.warn("执行HelloCommand的回退方法");
        return "error";
    }
}
