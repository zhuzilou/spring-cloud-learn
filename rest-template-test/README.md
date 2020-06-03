# RestTemplate负载均衡
RestTemplate本身只提供简单的API去调用HTTP服务，其本身不具有负载均衡的功能。

# @LoadBalanced
* 使用该注解修饰RestTemplate后，Spring容器在启动时，会为这些被修饰过的RestTemplate添加拦截器。
* 拦截器中使用了LoadBalancerClient来处理请求，LoadBalancerClient是Spring封装的负载均衡客户端，通过拦截处理，使RestTemplate拥有了负载均衡的功能。

# 简化版@LoadBalanced，拦截所有URI，跳转到固定URI。
## 自定义注解@MyLoadBalanced
使用**@Qualifier**，<a href="#为RestTemplate注入自定义拦截器">修饰在集合上时注入泛型实例</a>。
```java
/**
 * 自定义注解替代@LoadBalanced
 * Created by endless on 6/2/2020.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface MyLoadBalanced {
}
```
## 自定义HttpRequest，实现HttpRequest
改变所有请求的URI，主要关键点之一。
```java
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
            //String oldUri = sourceHttpRequest.getURI().toString();
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
```
## 自定义拦截器，实现ClientHttpRequestInterceptor
用于替换原有HttpRequest
```java
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
```
## 为RestTemplate注入自定义拦截器
* 由于自定义注解@MyLoadBalanced使用了**@Qualifier**，此注解修饰在集合上时，Spring容器中使用@MyLoadBalanced修饰的RestTemplate实例，都将被加入到该集合中，与@LoadBalanced加载方式相同。
* Spring容器启动完成后，非Lazy的Singleton都已被初始化完成（如RestTemplate）。使用[SmartInitializingSingleton](https://blog.csdn.net/god_86/article/details/105804051)对Bean进行扩展。
```java
@Slf4j
@Configuration
public class MyAutoConfiguration {
    @Autowired(required = false)
    //编译提示异常，因为检测到没有修饰的RestTemplate，创建Controller后异常消失。
    @MyLoadBalanced
    private final List<RestTemplate> myTemplates = Collections.emptyList();

    @Bean
    public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer() {
        log.info("==== 这个Bean将在容器初始化时创建 ====");
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (RestTemplate tpl : myTemplates) {
                    //创建一个自定义的拦截器实例
                    MyInterceptor mi = new MyInterceptor();
                    //获取RestTemplate原来的拦截器
                    List list = new ArrayList(tpl.getInterceptors());
                    //添加拦截器到集合
                    list.add(mi);
                    //将新的拦截器集合设置到RestTemplate实例
                    tpl.setInterceptors(list);
                }
            }
        };
    }
}
```
## 创建Controller正常使用RestTemplate
```java
@RestController
@Configuration
public class InvokerController {
    @Bean
    @MyLoadBalanced
    public RestTemplate getMyRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 浏览器访问的请求
     * @return
     */
    @RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String router() {
        RestTemplate restTemplate = getMyRestTemplate();
        String json = restTemplate.getForObject("http://my-server/hello", String.class);
        return json;
    }

    /**
     * 最终请求都会转到这个服务
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello world";
    }
}
```

# 小结
通过抄代码，一层一层模拟，感觉@LoadBalanced更像是个“全局出站拦截器”，通常全局拦截器用来拦截所有请求（入站）进行处理，崦这个将自己作为客户端再去请求其他服务时作拦截处理。
* Q1: 如果将请求某个服务器的方法抽象为一个方法，在方法内选择不同服务器，是否能达到同样效果？
* Q2: 将请求多个服务器的方法抽象为一个方法，是否就是加了注解的RestTemplate?