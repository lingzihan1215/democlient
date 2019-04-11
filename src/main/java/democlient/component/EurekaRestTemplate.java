package democlient.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class EurekaRestTemplate {
    @Autowired
    private SysInit sysInit;

    @Autowired
    private EurekaClientHttpRequestFactory eurekaClientHttpRequestFactory;
    @Autowired
    private MyClientHttpRequestInterceptor myClientHttpRequestInterceptor;

    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate(eurekaClientHttpRequestFactory);
            List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<>();
            interceptorList.add(myClientHttpRequestInterceptor);
            restTemplate.setInterceptors(interceptorList);
        }
        return restTemplate;
    }


}
