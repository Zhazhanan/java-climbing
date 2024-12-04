package tansun.creditx.bts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.cloud.nacos.config")
public class NacosConfigInfo {

    /**
     * Nacos server address.
     */
    private String serverAddr;

    /**
     * Data Id prefix.
     */
    private String prefix;

    /**
     * Nacos group.
     */
    private String group;

    /**
     * Nacos namespace.
     */
    private String namespace;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
