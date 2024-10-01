package com.example.kafka_service.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("producer")
public class KafkaPublisherRestController {

    @Autowired
    private KafkaTemplate<String, ObjectNode> kafkaTemplate;

    private static final String topic1 = "selenium_item";

    @GetMapping("publishType/scrapedItem")
    public String publishItemInfo(@PathVariable ObjectNode jsonItem) {
        kafkaTemplate.send(topic1, jsonItem);
        return "json item has been sent";
    }
}
