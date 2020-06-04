# Spring cloud整合Feign
* 客户端pom添加依赖，高版本feign变成openfeign
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
* 启动类上添加注解@EnableFeignClients
* 编写客户端接口，内容比较像服务端Controller的接口定义，使用@FeignClient修饰，需要声明调用服务的名称。
```java
@FeignClient("spring-feign-provider")
public interface PersonClient {
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String hello();

    @RequestMapping(method = RequestMethod.GET, value = "/person/{personId}")
    Person getPerson(@PathVariable("personId")Integer personId);
}
// -----------------
// 服务端Controller
// -----------------
@RestController
public class FirstController {
    @RequestMapping(value = "/person/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findPerson(@PathVariable("personId") Integer personId, HttpServletRequest request) {
        Person person = Person.builder()
                .id(personId)
                .name("CrazyIt")
                .age(30)
                .build();
        person.setMessage(request.getRequestURL().toString());
        return person;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(HttpServletRequest request) {
        return "Hello world " + request.getRequestURL().toString();
    }
}
```
* 客户端提供对外访问Controller
```java
@RestController
@Configuration
public class InvokerController {
    @Autowired
    private PersonClient personClient;

    @RequestMapping(value = "/invokeHello", method = RequestMethod.GET)
    public String invokeHello() {
        return personClient.hello();
    }

    @RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String router() {
        Person p = personClient.getPerson(2);
        return p.getMessage();
    }
}
```
