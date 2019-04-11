package democlient.controller;

import democlient.component.EurekaRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    @Autowired
    private EurekaRestTemplate eurekaRestTemplate;

    @GetMapping("/index.html")
    public String index() {
        RestTemplate restTemplate = eurekaRestTemplate.getRestTemplate();
        String msg = restTemplate.getForObject("http://appmodule/appmodule/api/appmodule/1", String.class);
        System.out.println(msg);
        return "ok";
    }
}
