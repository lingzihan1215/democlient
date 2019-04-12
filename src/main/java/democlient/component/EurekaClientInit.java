package democlient.component;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Component
@Lazy(value = false)
@Slf4j
public class EurekaClientInit implements InitializingBean {

    private DiscoveryClient eurekaClient;

    private void initEurekaClient() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("eureka-client.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        //
        properties.setProperty("eureka.ipAddr", "127.0.0.1");
        String instanceId = properties.getProperty("eureka.ipAddr") + ":" + properties.getProperty("eureka.ipAddr")
                + "/" + properties.getProperty("eureka.name");
        properties.setProperty("eureka.instanceId", instanceId);

        ConfigurationManager.loadProperties(properties);
        MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                .setAppName(instanceConfig.getAppname())
                .setDataCenterInfo(instanceConfig.getDataCenterInfo())
                .setLeaseInfo(LeaseInfo.Builder.newBuilder().build())
                .build();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);

        DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
        eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
    }


    public String getRealServerHost(String serviceId) {
        InstanceInfo serverInfo = eurekaClient.getNextServerFromEureka(serviceId, false);
        URI uri;
        try {
            uri = new URI(serverInfo.getHomePageUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String realServerName = uri.getHost() + ":" + uri.getPort();
        return realServerName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("开始初始化话eureka client");
        initEurekaClient();
    }
}
