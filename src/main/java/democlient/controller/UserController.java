package democlient.controller;

import democlient.component.EurekaRestTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    private EurekaRestTemplateBuilder eurekaRestTemplateBuilder;

    @GetMapping("/index.html")
    public String index() {
        RestTemplate restTemplate = eurekaRestTemplateBuilder.build(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                return execution.execute(request, body);
            }
        });
        String msg = restTemplate.getForObject("http://appmodule/appmodule/api/appmodule/1", String.class);
        System.out.println(msg);
        return "ok";
    }
}
