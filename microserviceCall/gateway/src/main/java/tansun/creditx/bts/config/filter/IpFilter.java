package tansun.creditx.bts.config.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * 自定义路由ip白名单过滤器
 */
public class IpFilter extends AbstractGatewayFilterFactory<IpFilter.Config> {

    public IpFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 获取ip地址
            String ip = getClientIp(exchange);
            // 检查ip白名单
            if (!config.getWhiteList().contains(ip)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private List<String> whiteList;

        public List<String> getWhiteList() {
            return whiteList;
        }

        public void setWhiteList(List<String> whiteList) {
            this.whiteList = whiteList;
        }
    }

    /**
     * 当前代码获取客户端 IP 的方法是通过 getRemoteAddress().getAddress().getHostAddress()，这对于直接通过 HTTP 请求的客户端来说是有效的。
     * 然而，如果你的应用部署在某些代理后（例如 Nginx 或者负载均衡器），你可能需要通过请求头 X-Forwarded-For 来获取真实的客户端 IP 地址
     *
     * @param exchange
     * @return
     */
    private String getClientIp(ServerWebExchange exchange) {
        String ip = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (ip == null) {
            ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}
