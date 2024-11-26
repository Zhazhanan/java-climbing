package tansun.creditx.bts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients({
        @LoadBalancerClient("nacos-customer")
})
public class GatewayApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(GatewayApplication.class, args);
    }
}
