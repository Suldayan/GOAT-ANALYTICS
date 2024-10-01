package com.example.selenium_kafka_service.controller;

import com.example.selenium_kafka_service.service.ItemPageService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scrape")
@CrossOrigin(origins = "http://localhost:3000")
public class WebscrapeController {

    @Autowired
    ItemPageService itemPageService;

    @GetMapping("/item")
    public List<ObjectNode> grabAllQueriedItems(
            @RequestParam(name = "item", required = true) String item,
            @RequestParam(name = "itemCount", defaultValue = "20") int itemCount) {
        return itemPageService.scrapeItems(item, itemCount);
    }
}
