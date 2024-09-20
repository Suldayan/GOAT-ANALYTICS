package com.example.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("item-service", r -> r.path("/item/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://item-service"))

                .route("email-service", r -> r.path("/email/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://email-service"))
                .build();
    }
}
