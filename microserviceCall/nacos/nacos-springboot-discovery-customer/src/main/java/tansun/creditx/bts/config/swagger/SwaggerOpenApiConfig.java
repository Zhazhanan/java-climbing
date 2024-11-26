package tansun.creditx.bts.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

/**
 * 这是一个普通的Swagger配置文档，其中不包含API接口的配置（API接口的配置推荐使用注解方式）
 *
 * @version 1.0
 **/
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerOpenApiConfig {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 项目负责人
     */
    private String projectLeader;

    /**
     * 项目负责人邮箱
     */
    private String leaderEmail;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 项目版本信息
     */
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    private String applicationDescription;

    /***
     * 构建Swagger3.0文档说明
     * @return 返回 OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {

        // 联系人信息(contact)
        Contact contact = new Contact().name(projectLeader)
                .email(leaderEmail)
                .url("https://www.tansun.com.cn/")
                .extensions(new HashMap<String, Object>()); // 使用Map配置信息（如key为"name","email","url"）

        // 授权许可信息(license)
        License license = new License().name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                .identifier("Apache-2.0")
                .extensions(new HashMap<String, Object>());// 使用Map配置信息（如key为"name","url","identifier"）

        //创建Api帮助文档的描述信息、联系人信息(contact)、授权许可信息(license)
        Info info = new Info().title(applicationName)
                .description(applicationDescription)
                .version(applicationVersion)
                .termsOfService("https://www.tansun.com.cn/")// Api接口的服务条款地址
                .license(license)
                .contact(contact);
        // 返回信息
        return new OpenAPI().openapi("3.0.1").info(info);
    }
}
