package tansun.creditx.bts.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import tansun.creditx.bts.config.filter.AuthFilter;
import tansun.creditx.bts.config.filter.IpFilter;

/**
 * 配置javabean加载顺序
 */
@Configuration
public class GatewayConfig {

    // ip白名单过滤器
    @Bean
    @Order(1)
    public IpFilter ipFilter() {
        return new IpFilter();
    }

    // 全局过滤器
    @Bean
    @Order(0)
    public GlobalFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public ConfigListener configListener() {
        return new ConfigListener();
    }

}
