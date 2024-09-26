package com.example.gateway;

import com.example.gateway.config.AuthenticationFilter;
import com.example.gateway.config.JwtUtil;
import com.example.gateway.config.RouterValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

import static org.mockito.Mockito.*;

class AuthenticationFilterTest {

    @Mock
    private RouterValidator routerValidator;

    @Mock
    private JwtUtil jwtUtil;

    private AuthenticationFilter authFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock Predicate
        Predicate<ServerHttpRequest> isSecuredMock = mock(Predicate.class);

        // Set the mock Predicate as the value of isSecured
        when(routerValidator.isSecured).thenReturn(isSecuredMock);

        authFilter = new AuthenticationFilter(routerValidator, jwtUtil);
    }

    @Test
    void testNonSecuredPath() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/public"));

        // Set the behavior for this specific test
        when(routerValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(false);

        Mono<Void> result = authFilter.filter(exchange, (serverWebExchange) -> Mono.empty());

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testSecuredPathWithValidToken() {
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/secured").header("Authorization", "validToken")
        );
        when(routerValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(true);
        when(jwtUtil.isInvalid("validToken")).thenReturn(false);

        Mono<Void> result = authFilter.filter(exchange, (serverWebExchange) -> Mono.empty());

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testSecuredPathWithInvalidToken() {
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/secured").header("Authorization", "invalidToken")
        );
        when(routerValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(true);
        when(jwtUtil.isInvalid("invalidToken")).thenReturn(true);

        Mono<Void> result = authFilter.filter(exchange, (serverWebExchange) -> Mono.empty());

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void testSecuredPathWithMissingToken() {
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/secured")
        );
        when(routerValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(true);

        Mono<Void> result = authFilter.filter(exchange, (serverWebExchange) -> Mono.empty());

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}