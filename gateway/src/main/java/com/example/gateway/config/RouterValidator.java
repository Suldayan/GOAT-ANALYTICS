package com.example.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

// This component is used for specifying any endpoints that we want to be public
// without any jwt token claim or authorization

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/item/graph-analytics",
            "/item/metadata"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
