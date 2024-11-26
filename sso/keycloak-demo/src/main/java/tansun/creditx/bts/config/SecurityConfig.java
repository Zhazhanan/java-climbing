package tansun.creditx.bts.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * SecurityFilterChain 是 Spring Security 5.4 之后引入的新概念，它替代了传统的基于继承 WebSecurityConfigurerAdapter 的安全配置方式。
 * 在 Spring Security 5.7 中，WebSecurityConfigurerAdapter 已被标记为弃用，
 * 推荐使用 SecurityFilterChain 来定义安全过滤器链（filter chain）的配置
 */
@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${spring.security.oauth2.client.registration.keycloak.redirect-uri}")
    private String oauth2LogoutRedirectUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http.headers()
                // 允许来自同域的 iframe 嵌入
                .frameOptions().sameOrigin()
                .and().authorizeRequests()
                // 允许公共路径请求
                .antMatchers("/","/error", "/public/**", "/login/*", "/logout", "/css/**", "/static/js/**", "/fonts/**", "/images/**", "/i/**", "/resources/**", "/image/**", "/actuator/**", "/custom/metrics/**").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/oauth2/authorization/keycloak")
                .defaultSuccessUrl("/menu", true)
//                .successHandler(authenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        // 设置退出成功后的重定向 URL
        log.info("退出成功后的重定向 URL: ${}", oauth2LogoutRedirectUrl);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri(oauth2LogoutRedirectUrl);
        return oidcLogoutSuccessHandler;
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String authType = request.getAuthType();
            log.info("authType: ${}", authType);
            // 认证成功后的逻辑，可以是重定向到主页
            response.sendRedirect("/");  // 登录成功后重定向到根路径
//            response.sendRedirect("/dashboard");  // 登录成功后重定向到根路径
        };
    }
}
