package tansun.creditx.bts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProducerApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(NacosProducerApplication.class, args);
    }
}
