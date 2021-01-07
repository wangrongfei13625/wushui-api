package com.huaxin.gatwayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
public class GatwayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatwayApiApplication.class, args);
    }

}
