package democlient.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EurekaRestTemplateBuilder {
    @Autowired
    private SysInit sysInit;
    private static Map<String, RestTemplate> restTemplates = new HashMap<>();

    public synchronized RestTemplate build(ClientHttpRequestInterceptor... interceptors) {
        return build("default", interceptors);
    }

    public synchronized RestTemplate build(String name, ClientHttpRequestInterceptor... interceptors) {

        if (restTemplates.containsKey(name)) return restTemplates.get(name);

        SimpleClientHttpRequestFactory factory = new EurekaClientHttpRequestFactory();
        factory.setReadTimeout(15000);//ms
        factory.setConnectTimeout(5000);//ms

        RestTemplate restTemplate = new RestTemplate(factory);
        List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<>();
        //interceptorList.add(new RestTemplateAutoHeaderInterceptor());
        if (interceptors != null && interceptors.length > 0) {
            for (ClientHttpRequestInterceptor interceptor : interceptors) {
                interceptorList.add(interceptor);
            }
        }
        restTemplate.setInterceptors(interceptorList);
        //
        //restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        //
        restTemplates.put(name, restTemplate);

        return restTemplate;
    }

    private class EurekaClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

        @Override
        public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
            uri = convertToRealUri(uri);
            return super.createRequest(uri, httpMethod);
        }

        @Override
        public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
            uri = convertToRealUri(uri);
            return super.createAsyncRequest(uri, httpMethod);
        }

        private URI convertToRealUri(URI uri) {
            String serviceId = uri.getHost();
            try {
                String realHost = sysInit.getRealServerHost(serviceId);
                URI uri2 = new URI(uri.toString().replace("http://" + serviceId, "http://" + realHost));
                return uri2;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
