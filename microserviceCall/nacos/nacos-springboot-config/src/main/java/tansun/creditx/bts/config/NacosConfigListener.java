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

public class NacosConfigListener {
    private static Logger log = LoggerFactory.getLogger(NacosConfigListener.class);

    @Autowired
    private NacosConfigManager nacosConfigManager;

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


    /**
     * 监控自身配置
     *
     * @throws NacosException
     */
    @PostConstruct
    private void init() throws NacosException {
        this.listenForConfigChanges(dataId, group);
    }

    /**
     * 监听配置变更
     */
    public void listenForConfigChanges(String dataId, String group) throws NacosException {
        ConfigService configService = nacosConfigManager.getConfigService();


        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("receiveConfigInfo:: configInfo:{}", configInfo);
                // 你可以在这里处理收到的新配置

            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

}
