package tansun.creditx.bts.config;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


/**
 * 禁用ssl证书验证,仅用于本地开发环境，联调服务器keycloak
 */
public class TrustAllCertificates {
    public static void trustAllCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing, trust all client certs
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing, trust all server certs
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
}

