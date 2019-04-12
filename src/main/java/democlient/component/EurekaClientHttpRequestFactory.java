package democlient.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class EurekaClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    @Autowired
    private EurekaClientInit eurekaClientInit;

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
            String realHost = eurekaClientInit.getRealServerHost(serviceId);
            URI uri2 = new URI(uri.toString().replace("http://" + serviceId, "http://" + realHost));
            return uri2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
