package tansun.creditx.bts.example;


import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import tansun.creditx.bts.config.SwaggerConfig;
import tansun.creditx.bts.config.SwaggerConfigInfo;
import tansun.creditx.bts.service.NacosSwaggerConfigService;

import javax.annotation.Resource;

@SpringBootTest
public class NacosSwaggerConfigServiceTest {
    private static Logger log = LoggerFactory.getLogger(NacosSwaggerConfigServiceTest.class);

    @Resource
    private NacosSwaggerConfigService nacosConfigService;
    @Resource
    private SwaggerConfig swaggerConfig;

    @Test
    public void updateSwaggerEnableConfig() throws NacosException {
        swaggerConfig.getList().forEach(v -> System.out.println(v.toString()));

        if (swaggerConfig != null) {
            for (SwaggerConfigInfo swaggerConfigInfo : swaggerConfig.getList()) {
                String dataId = swaggerConfigInfo.getDataId();
                String group = swaggerConfigInfo.getGroup();
                boolean enable = swaggerConfigInfo.isEnable();
                log.info("updateSwaggerEnableConfig:: dataId:{}, group:{}, enable:{},", dataId, group, enable);
                // 更新 swagger 开关配置
                nacosConfigService.updateSwaggerEnableConfig(dataId, group, enable);
            }
        }
    }
}