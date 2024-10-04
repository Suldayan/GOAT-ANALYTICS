package com.example.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    // TODO: add circuit breaking to respective services (selenium and kafka)

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("user-service", r -> r.path("/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083"))
                .build();
    }
}
