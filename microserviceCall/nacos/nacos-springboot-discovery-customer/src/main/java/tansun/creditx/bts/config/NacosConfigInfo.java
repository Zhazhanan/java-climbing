package tansun.creditx.bts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "creditx")
@Component
@RefreshScope
public class NacosConfigInfo {

    /**
     * Nacos group.
     */
    private String group;

    /**
     * Nacos namespace.
     */
    private String names;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
