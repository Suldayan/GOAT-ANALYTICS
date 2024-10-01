package com.example.kafka_service.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    List<ObjectNode> messages = new ArrayList<>();

    @KafkaListener(topics = "selenium_item")
    public List<ObjectNode> getMessageFromTopic(ObjectNode data) {
        messages.add(data);
        logger.info("message: {}", data);
        return messages;
    }
}
