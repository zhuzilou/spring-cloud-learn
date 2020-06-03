package cc.lostyouth.springcloud.resttemplatetest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by endless on 6/2/2020.
 */
@Slf4j
public class MyInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        log.info("==========  这是自定义拦截器实现");
        log.info("            原来的URI：" + httpRequest.getURI());
        //转换成新的请求对象（更换URI）
        MyHttpRequest newRequest = new MyHttpRequest(httpRequest);
        log.info("            拦截后新的URI：" + newRequest.getURI());
        return clientHttpRequestExecution.execute(newRequest, bytes);
    }
}
