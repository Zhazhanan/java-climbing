package tansun.creditx.bts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosConfig {

    // java bean config
    @Bean
    public NacosConfigInfo nacosConfigInfo() {
        return new NacosConfigInfo();
    }

    @Bean
    public NacosConfigListener nacosConfigListener() {
        return new NacosConfigListener();
    }

    @Bean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }


}

