package tansun.creditx.bts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tansun.creditx.bts.config.TrustAllCertificates;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Hello world!
 */
@SpringBootApplication
public class KeycloakApplication {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        /**
         * 禁用ssl证书验证,仅用于本地开发环境，联调服务器keycloak
         */
        TrustAllCertificates.trustAllCertificates();
        SpringApplication.run(KeycloakApplication.class, args);
    }
}
