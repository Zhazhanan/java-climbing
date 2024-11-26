package tansun.creditx.bts.config.blackwhitelist;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接口白名单
 */
@Component
@ConfigurationProperties("url")
public class URLWhiteList {

    /* 接口白名单*/
    public List<String> whitelist;

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }
}
