package tansun.creditx.bts.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Nacos Configuration listener.
 */
public class ConfigListener {

    Logger logger = LoggerFactory.getLogger(ConfigListener.class);

    /**
     * Nacos dataId.
     */
    @Value("${spring.config.import}")
    private String dataId;

    /**
     * Nacos group.
     */
    @Value("${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
    private String group;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @PostConstruct
    public void init() throws NacosException {
        ConfigService configService = nacosConfigManager.getConfigService();
        String[] split = dataId.split(":");
        if (split.length > 2) {
            dataId = split[2];
        }

        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.info("[dataId]:[" + dataId + "],Configuration changed to:" + configInfo);
            }
        });
    }

}