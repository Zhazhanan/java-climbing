package tansun.creditx.bts.service;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tansun.creditx.bts.config.SwaggerConfig;
import tansun.creditx.bts.config.SwaggerConfigInfo;
import tansun.creditx.bts.tools.NacosFileTool;

@Service
public class NacosSwaggerConfigService {
    private static Logger log = LoggerFactory.getLogger(NacosSwaggerConfigService.class);

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private SwaggerConfig swaggerConfig;

    // 读取配置超时时间,5秒
    private long timeoutMs = 5000L;

    /**
     * 动态更新 swagger 开关配置
     */
    public void updateSwaggerEnableConfig(String dataId, String group, boolean enableSwagger) throws NacosException {
        log.info("updateSwaggerEnableConfig:: dataId:{},group:{},enableSwagger:{}", dataId, group, enableSwagger);
        ConfigService configService = nacosConfigManager.getConfigService();
        // 获取当前配置
        String currentConfig = configService.getConfig(dataId, group, timeoutMs);
        if (StringUtil.isNullOrEmpty(currentConfig)) {
            log.error("updateSwaggerEnableConfig:: dataId:{},group:{}, 查询内容为空", dataId, group);
        }
        log.info("当前配置:{}", currentConfig);

        // 获取nacos文件类型
        ConfigType configType = NacosFileTool.configType(currentConfig);

        // 根据配置类型替换不同开关
        switch (configType) {
            case YAML:
                log.info("配置文件是 YAML 格式");
                currentConfig = currentConfig.replaceAll("(swagger:\\s*enable:\\s*)(true|false)", "$1" + enableSwagger);
                break;
            case PROPERTIES:
                log.info("配置文件是 Properties 格式");
                currentConfig = currentConfig.replaceAll("swagger\\.enable\\s*=\\s*(true|false)", "swagger.enable=" + enableSwagger);
                break;
            default:
                log.error("无法识别的配置文件格式");
                break;
        }

        // 发布更新到 Nacos
        boolean isUpdated = configService.publishConfig(dataId, group, currentConfig, configType.getType());
        if (isUpdated) {
            log.info("配置已更新:: dataId:{},group:{},enableSwagger:{}", dataId, group, enableSwagger);
        } else {
            log.info("配置更新失败:: dataId:{},group:{},enableSwagger:{}", dataId, group, enableSwagger);
        }
    }

    public void swaggerConfigFlush() throws NacosException {
        if (swaggerConfig != null) {
            for (SwaggerConfigInfo swaggerConfigInfo : swaggerConfig.getList()) {
                String dataId = swaggerConfigInfo.getDataId();
                String group = swaggerConfigInfo.getGroup();
                boolean enable = swaggerConfigInfo.isEnable();
                // 更新 swagger 开关配置
                this.updateSwaggerEnableConfig(dataId, group, enable);
            }
        }
    }

}
