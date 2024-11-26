package tansun.creditx.bts.config.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tansun.creditx.bts.config.blackwhitelist.URLWhiteList;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.alibaba.nacos.api.common.Constants.TOKEN;

/**
 * 全局过滤器
 */
@Slf4j
@ConditionalOnBean(URLWhiteList.class)
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    private URLWhiteList urlWhiteList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求url
        String url = exchange.getRequest().getPath().value();
        log.info("get request url {}", url);

        // 接口白名单放行
        if (urlWhiteList.getWhitelist().contains(url)) {
            return chain.filter(exchange);
        }

        // 非接口白名单， token校验，鉴权？
        if (!urlWhiteList.getWhitelist().contains(url)) {
            List<String> tokenList = exchange.getRequest().getHeaders().get(TOKEN);
            log.info("token: {}", tokenList);

            //  TODO 只校验了token是否为空，应校验正确性
            if (CollectionUtils.isEmpty(tokenList)) {
                ServerHttpResponse response = exchange.getResponse();
                // 错误信息
                byte[] data = JSONUtil.toJsonStr("Token is null").getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                return response.writeWith(Mono.just(buffer));
            }
        }


        return chain.filter(exchange);
    }

    // 执行级别
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
