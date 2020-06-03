package cc.lostyouth.springcloud.resttemplatetest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

import java.net.URI;

/**
 * 拦截HttpRequest，实现自定义转换
 * Created by endless on 6/2/2020.
 */
@Slf4j
public class MyHttpRequest implements HttpRequest {
    private final HttpRequest sourceHttpRequest;

    public MyHttpRequest(HttpRequest sourceHttpRequest) {
        this.sourceHttpRequest = sourceHttpRequest;
    }

    @Override
    public String getMethodValue() {
        return this.sourceHttpRequest.getMethodValue();
    }

    @Override
    public URI getURI() {
        try {
            String oldUri = sourceHttpRequest.getURI().toString();
            //转换URI
            URI newUri = new URI("http://localhost:8080/hello");
            return newUri;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sourceHttpRequest.getURI();
    }

    @Override
    public HttpHeaders getHeaders() {
        return sourceHttpRequest.getHeaders();
    }
}
