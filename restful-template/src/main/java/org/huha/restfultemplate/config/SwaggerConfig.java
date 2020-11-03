package org.huha.restfultemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangkun
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Value("${swagger.enabled:false}")
    private boolean swaggerabled;
    @Value("${swagger.api.info.title:''}")
    private String title;
    @Value("${swagger.api.info.description:''}")
    private String description;
    @Value("${swagger.api.info.version:1.0.0}")
    private String version;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(swaggerabled).apiInfo(getApiInfo()).groupName("service-api")
                .select().apis(RequestHandlerSelectors.basePackage("org.youqiu.transaction"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title(title).description(description).version(version).termsOfServiceUrl("")
//                 .license("Apache 2.0")
//                 .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}
