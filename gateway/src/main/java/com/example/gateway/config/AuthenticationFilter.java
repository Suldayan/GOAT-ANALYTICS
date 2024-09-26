package com.example.gateway.config;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
* AuthenticationFilter is a gateway filter responsible for filtering out invalid JWT based tokens.
* It intercepts requests from the user, and validates the token (created in the frontend via firebase auth).
* Upon successful validation, the user will be granted with the services provided.
* */

@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;

    /*
     * @param routeValidator is used to determine whether the route is public or not
     * @param jwtUtil used for parsing the jwt token and validation (token expiration)
     * */
    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    /*
    * Filters incoming requests, performing JWT authentication for secured routes.
    *
    * @param exchange the current server exchange
    * @param chain the filter chain
    * @return a Mono<Void> representing the completion of the filter process
    * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            log.debug("Processing secured request: {}", request.getPath());

            if (this.isAuthMissing(request)) {
                log.error("Authentication missing for request. Path: {}, Status: {}",
                        request.getPath(), HttpStatus.UNAUTHORIZED);
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = this.getAuthHeader(request);

            if (jwtUtil.isInvalid(token)) {
                log.error("Invalid token for request. Path: {}, Status: {}",
                        request.getPath(), HttpStatus.FORBIDDEN);
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }

            this.updateRequest(exchange, token);
            log.info("Request successfully updated. Path: {}", request.getPath());
        } else {
            log.debug("Processing non-secured request: {}", request.getPath());
        }
        return chain.filter(exchange);
    }

    /*
    * Handles authentication errors by setting the correct HTTP status upon response
    *
    * @param exchange the current server exchange
    * @param httpStatus the HTTP status to set on the response
    * @return a Mono<Void> representing the completion of the error handling
    * */
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * Extracts the Authorization header from the request.
     *
     * @param request the incoming server request
     * @return the Authorization header value, or null if not present
     */
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").getFirst();
    }

    /**
     * Checks if the Authorization header is missing from the request.
     *
     * @param request the incoming server request
     * @return true if the Authorization header is missing, false otherwise
     */
    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    /**
     * Updates the request with user information extracted from the JWT token.
     *
     * @param exchange the current server exchange
     * @param token the JWT token
     */
    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        String email = claims.get("email", String.class);
        exchange.getRequest().mutate()
                .header("email", email)
                .build();
        log.debug("Request updated with email: {}", email);
    }
}