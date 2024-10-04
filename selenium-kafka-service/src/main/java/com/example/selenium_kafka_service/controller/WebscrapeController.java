package com.example.selenium_kafka_service.controller;

import com.example.selenium_kafka_service.service.ItemPageService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/scrape")
public class WebscrapeController {

    private static final Logger logger = LoggerFactory.getLogger(WebscrapeController.class);

    @Autowired
    ItemPageService itemPageService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/items")
    public List<ObjectNode> grabAllQueriedItems(
            @RequestParam(name = "item", required = true) String item,
            @RequestParam(name = "itemCount", defaultValue = "20") int itemCount) {
        return itemPageService.scrapeItems(item, itemCount);
    }

    @GetMapping("/kafka")
    @CircuitBreaker(name = "kafkaService", fallbackMethod = "fallbackKafkaService")
    @Retry(name = "kafkaService")
    @RateLimiter(name = "kafkaService")
    public void sendItemPriceToKafka(ObjectNode item) {
        // URL of the Selenium service
        String kafkaServiceUrl = "http://localhost:8083/kafka/items?itemQuery=" + item;

        // Send GET request to Selenium microservice and retrieve items
        try {
            ResponseEntity<ObjectNode> response = restTemplate.exchange(
                    kafkaServiceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ObjectNode>() {}
            );

            logger.info("Item price has been successfully sent to Kafka! {}", item);
        } catch (Exception e) {
            logger.error("Error sending the item to Kafka: {}", e);
            throw e;
        }
    }

    public String fallbackKafkaService(ObjectNode item, Throwable t) {
        logger.warn("Fallback triggered due to: {}", t.getMessage());
        return "Kafka is currently down. Please try again later";
    }
}
