package tansun.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import tansun.swagger.DocketInfo;
import tansun.swagger.GlobalOperationParameter;
import tansun.swagger.SwaggerProperties;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 类说明: swagger 配置类
 *
 * @date 2022/7/26 10:23
 */
@Configuration
@EnableOpenApi     //开启 Swagger3，可以不写
@EnableKnife4j     //开启 knife4j，可以不写
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration implements BeanFactoryAware {

    private static final String AUTH_KEY = "Authorization";

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "tulip.swagger.enabled", matchIfMissing = true)
    public List<Docket> createRestApi(SwaggerProperties swaggerProperties) {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();
        // 没有分组
        if (swaggerProperties.getDocket().size() == 0) {
            final Docket docket = createDocket(swaggerProperties);
            configurableBeanFactory.registerSingleton("defaultDocket", docket);
            docketList.add(docket);
            return docketList;
        }

        // 分组创建
        for (String groupName : swaggerProperties.getDocket().keySet()) {
            DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);

            ApiInfo apiInfo = new ApiInfoBuilder().title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle()).description(docketInfo.getDescription().isEmpty() ? swaggerProperties.getDescription() : docketInfo.getDescription()).version(docketInfo.getVersion().isEmpty() ? swaggerProperties.getVersion() : docketInfo.getVersion()).license(docketInfo.getLicense().isEmpty() ? swaggerProperties.getLicense() : docketInfo.getLicense()).licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerProperties.getLicenseUrl() : docketInfo.getLicenseUrl()).contact(new Contact(docketInfo.getContact().getName().isEmpty() ? swaggerProperties.getContact().getName() : docketInfo.getContact().getName(), docketInfo.getContact().getUrl().isEmpty() ? swaggerProperties.getContact().getUrl() : docketInfo.getContact().getUrl(), docketInfo.getContact().getEmail().isEmpty() ? swaggerProperties.getContact().getEmail() : docketInfo.getContact().getEmail())).termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty() ? swaggerProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl()).build();


            Docket docket = new Docket(DocumentationType.OAS_30).host(swaggerProperties.getHost()).apiInfo(apiInfo).globalRequestParameters(assemblyGlobalOperationParameters(swaggerProperties.getGlobalOperationParameters(), docketInfo.getGlobalOperationParameters())).groupName(groupName).select().apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage())).paths(buildPredicateSelector(docketInfo)).build().securitySchemes(securitySchemes()).securityContexts(securityContexts());

            configurableBeanFactory.registerSingleton(groupName, docket);
            docketList.add(docket);
        }
        return docketList;
    }

    /**
     * 创建 Docket对象
     *
     * @param swaggerProperties swagger配置
     * @return Docket
     */
    private Docket createDocket(final SwaggerProperties swaggerProperties) {
        ApiInfo apiInfo = new ApiInfoBuilder().title(swaggerProperties.getTitle()).description(swaggerProperties.getDescription()).version(swaggerProperties.getVersion()).license(swaggerProperties.getLicense()).licenseUrl(swaggerProperties.getLicenseUrl()).contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail())).termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()).build();

        DocketInfo dockerInfo = new DocketInfo();
        dockerInfo.setBasePackage(swaggerProperties.getBasePackage());
        dockerInfo.setExcludePath(swaggerProperties.getExcludePath());

        return new Docket(DocumentationType.OAS_30).host(swaggerProperties.getHost()).apiInfo(apiInfo).enable(swaggerProperties.getEnabled()).globalRequestParameters(buildGlobalOperationParametersFromSwaggerProperties(swaggerProperties.getGlobalOperationParameters())).select().apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).paths(buildPredicateSelector(dockerInfo)).build().securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>(1);
        SecurityContext securityContext = SecurityContext.builder().securityReferences(defaultAuth()).build();
        contexts.add(securityContext);
        return contexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> references = new ArrayList<>(1);
        references.add(new SecurityReference(AUTH_KEY, authorizationScopes));
        return references;
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeys = new ArrayList<>(1);
        ApiKey apiKey = new ApiKey(AUTH_KEY, AUTH_KEY, "header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    /**
     * 生成swagger全局通用参数 如：请求token
     */
    private List<RequestParameter> buildGlobalOperationParametersFromSwaggerProperties(List<GlobalOperationParameter> globalOperationParameters) {

        List<RequestParameter> parameters = Lists.newArrayList();
        if (Objects.isNull(globalOperationParameters)) {
            return parameters;
        }
        for (GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
            parameters.add(new RequestParameterBuilder()
                    //参数名称
                    .name(globalOperationParameter.getName())
                    //参数说明
                    .description(globalOperationParameter.getDescription())
                    //参数存放位置
                    .in(globalOperationParameter.getParameterType())
                    //参数数据类型 暂时写死为string 有其他需求再改
                    .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                    //参数是否必须
                    .required(Boolean.parseBoolean(globalOperationParameter.getRequired())).build());
        }
        return parameters;
    }


    /**
     * 局部参数按照name覆盖局部参数
     *
     * @param globalOperationParameters
     * @param docketOperationParameters
     * @return
     */
    private List<RequestParameter> assemblyGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters, List<GlobalOperationParameter> docketOperationParameters) {

        if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
            return buildGlobalOperationParametersFromSwaggerProperties(globalOperationParameters);
        }

        Set<String> docketNames = docketOperationParameters.stream().map(GlobalOperationParameter::getName).collect(Collectors.toSet());

        List<GlobalOperationParameter> resultOperationParameters = Lists.newArrayList();

        if (Objects.nonNull(globalOperationParameters)) {
            for (GlobalOperationParameter parameter : globalOperationParameters) {
                if (!docketNames.contains(parameter.getName())) {
                    resultOperationParameters.add(parameter);
                }
            }
        }

        resultOperationParameters.addAll(docketOperationParameters);
        return buildGlobalOperationParametersFromSwaggerProperties(resultOperationParameters);
    }

    private java.util.function.Predicate<String> buildPredicateSelector(DocketInfo config) {
        // 当没有配置任何path的时候，解析/**
        if (config.getBasePath().isEmpty()) {
            config.getBasePath().add("/**");
        }
        List<java.util.function.Predicate<String>> basePath = new ArrayList<>();
        for (String path : config.getBasePath()) {
            basePath.add(PathSelectors.ant(path));
        }

        // exclude-path处理
        List<java.util.function.Predicate<String>> excludePath = new ArrayList<>();
        for (String path : config.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        // 当没有配置任何path的时候，解析/.*
        if (config.getBasePath().isEmpty() || config.getBasePath() == null) {
            return PathSelectors.any().and(excludePath.stream().reduce(each -> true, (a, b) -> a.and(b.negate())));
        }

        //组装 base-path 和 exclude-path each为false原因是，如果是true，有任何or，都不会走右边
        return basePath.stream().reduce(each -> false, Predicate::or).and(excludePath.stream().reduce(each -> true, (a, b) -> a.and(b.negate())));

    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}