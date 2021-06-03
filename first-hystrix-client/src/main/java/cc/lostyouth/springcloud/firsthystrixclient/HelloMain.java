package cc.lostyouth.springcloud.firsthystrixclient;

import cc.lostyouth.springcloud.firsthystrixclient.config.HelloCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by endless on 6/5/2020.
 */
@Slf4j
public class HelloMain {
    public static void main(String[] args) {
        //请求正常的服务
        String normalUrl = "http://localhost:8080/normalHello";
        HelloCommand command = new HelloCommand(normalUrl);
        String result = command.execute();
        log.info("请求正常的服务，结果：" + result);
    }
}
