package com.example.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @RequestMapping("/fallback")
    public ResponseEntity<String> fallback() {

        return new ResponseEntity<>("Service is temporary unavailable. Please try again later.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }
}
