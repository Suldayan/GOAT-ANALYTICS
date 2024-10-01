package com.example.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/*
* RouteValidator is used to validate any routes to the public without the need for authentication.
* Any route defined in here can be accessed by anybody.
* */

@Component
public class RouterValidator {

    /*
    * A list of all the open api endpoints that our user and other services can use to interact
    * with each other.
    * */
    public static final List<String> openApiEndpoints = List.of(
            "/item/graph-analytics",
            "/item/metadata"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
