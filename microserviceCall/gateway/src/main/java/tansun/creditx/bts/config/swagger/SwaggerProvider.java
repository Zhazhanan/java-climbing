//package tansun.creditx.bts.config.swagger;
//
//import lombok.AllArgsConstructor;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.support.NameUtils;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Primary
//@AllArgsConstructor
//public class SwaggerProvider implements SwaggerResourcesProvider {
//
//    public static final String API_URI = "/v3/api-docs";
//
//    private final RouteLocator routeLocator;
//
//    private final GatewayProperties gatewayProperties;
//
//    /**
//     * 这个类是整合的核心,主要切换Select a definition选择框。
//     * SwaggerResource设置子服务的name、location、version
//     * 根据服务名切换
//     */
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//        // 取出Spring Cloud Gateway中的route
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//        // 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
//        gatewayProperties.getRoutes().stream().filter(routeDefinition ->
//                        routes.contains(routeDefinition.getId()))
//                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
//                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                        .forEach(predicateDefinition ->
//                                resources.add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs()
//                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0")
//                                        .replace("/**", API_URI)))));
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion("3.0.0");
//        return swaggerResource;
//    }
//
//}
