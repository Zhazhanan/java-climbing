package tansun.creditx.bts.example;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tansun.creditx.bts.config.NacosConfigListener;

/**
 * Example of docking with Nacos interface.
 *
 * @author lixiaoshuang
 */
@RestController
@RequestMapping("/nacos")
public class DockingInterfaceExample {

    Logger logger = LoggerFactory.getLogger(DockingInterfaceExample.class);

    /**
     * Nacos group.
     */
    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private NacosConfigListener nacosConfigListener;

    /**
     * Get configuration information.
     *
     * @param dataId dataId
     * @param group  group
     * @return config
     */
    @RequestMapping("/getConfig")
    public String getConfig(@RequestParam("dataId") String dataId,
                            @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.getConfig(dataId, group, 2000);
    }

    /**
     * Publish configuration.
     *
     * @param dataId  dataId
     * @param group   group
     * @param content content
     * @return boolean
     */
    @RequestMapping("/publishConfig")
    public boolean publishConfig(@RequestParam("dataId") String dataId,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam("content") String content) throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.publishConfig(dataId, group, content);
    }

    /**
     * Delete configuration.
     *
     * @param dataId dataId
     * @param group  group
     * @return boolean
     */
    @RequestMapping("/removeConfig")
    public boolean removeConfig(@RequestParam("dataId") String dataId,
                                @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.removeConfig(dataId, group);
    }

    /**
     * Add listener configuration information.
     *
     * @param dataId dataId
     * @param group  group
     */
    @RequestMapping("/listener")
    public String listenerConfig(@RequestParam("dataId") String dataId,
                                 @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }

        nacosConfigListener.listenForConfigChanges(dataId, group);
        return "Add Lister successfully!";
    }
}
