package cc.lostyouth.springcloud.firstcloudinvoker;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FirstCloudInvokerApplicationTests {

    @Test
    void contextLoads() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < 6; i++) {
            HttpGet httpGet = new HttpGet("http://localhost:9000/router");
            HttpResponse response = httpClient.execute(httpGet);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

}
