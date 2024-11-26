//package tansun.creditx.bts.config.swagger;
//
//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//
//@Configuration
//@EnableOpenApi
//public class SwaggerConfig {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
//                //apis() 控制哪些接口暴露给swagger，
//                // RequestHandlerSelectors.any() 所有都暴露
//                // RequestHandlerSelectors.basePackage("net.xdclass.*")  指定包位置
//                // withMethodAnnotation(ApiOperation.class)标记有这个注解 ApiOperation
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("接口文档")
//                .description("更多请咨询服务开发者")
//                //附加信息
//                .contact(new Contact("Data", "http://www.baidu.com", "email@111.com"))
//                .version("1.0")//版本
//                .build();
//    }
//}
