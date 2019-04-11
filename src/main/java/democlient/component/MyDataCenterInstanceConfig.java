package democlient.component;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.EurekaInstanceConfig;

import java.util.Map;

public class MyDataCenterInstanceConfig implements EurekaInstanceConfig {
    @Override
    public String getInstanceId() {
        return "test:ip";
    }

    @Override
    public String getAppname() {
        return "test";
    }

    @Override
    public String getAppGroupName() {
        return null;
    }

    @Override
    public boolean isInstanceEnabledOnit() {
        return false;
    }

    @Override
    public int getNonSecurePort() {
        return 0;
    }

    @Override
    public int getSecurePort() {
        return 0;
    }

    @Override
    public boolean isNonSecurePortEnabled() {
        return true;
    }

    @Override
    public boolean getSecurePortEnabled() {
        return false;
    }

    @Override
    public int getLeaseRenewalIntervalInSeconds() {
        return 0;
    }

    @Override
    public int getLeaseExpirationDurationInSeconds() {
        return 0;
    }

    @Override
    public String getVirtualHostName() {
        return null;
    }

    @Override
    public String getSecureVirtualHostName() {
        return null;
    }

    @Override
    public String getASGName() {
        return null;
    }

    @Override
    public String getHostName(boolean b) {
        return null;
    }

    @Override
    public Map<String, String> getMetadataMap() {
        return null;
    }

    @Override
    public DataCenterInfo getDataCenterInfo() {
        return new DataCenterInfo() {
            @Override
            public Name getName() {
                return Name.Netflix;
            }
        };
    }

    @Override
    public String getIpAddress() {
        return null;
    }

    @Override
    public String getStatusPageUrlPath() {
        return null;
    }

    @Override
    public String getStatusPageUrl() {
        return null;
    }

    @Override
    public String getHomePageUrlPath() {
        return null;
    }

    @Override
    public String getHomePageUrl() {
        return null;
    }

    @Override
    public String getHealthCheckUrlPath() {
        return null;
    }

    @Override
    public String getHealthCheckUrl() {
        return null;
    }

    @Override
    public String getSecureHealthCheckUrl() {
        return null;
    }

    @Override
    public String[] getDefaultAddressResolutionOrder() {
        return new String[0];
    }

    @Override
    public String getNamespace() {
        return null;
    }
}
