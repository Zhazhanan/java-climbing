package tansun.creditx.bts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

@ConfigurationProperties(prefix = "swagger.configurations")
@RefreshScope
public class SwaggerConfig {

    private boolean enable;

    private List<SwaggerConfigInfo> list;

    public List<SwaggerConfigInfo> getList() {
        return list;
    }

    public void setList(List<SwaggerConfigInfo> list) {
        this.list = list;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
