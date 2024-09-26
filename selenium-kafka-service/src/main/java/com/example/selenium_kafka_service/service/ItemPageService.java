package com.example.selenium_kafka_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemPageService {

    private final ObjectMapper objectMapper;

    @Autowired
    WebscrapeService scrapeService;

    public ItemPageService() {
        this.objectMapper = new ObjectMapper();
    }

    public List<ObjectNode> scrapeItems(String searchQuery, int itemCount) {
        List<ObjectNode> items = new ArrayList<>();
        scrapeService.navigateToSearchPage(searchQuery);

        for (int i = 1; i <= itemCount; i++) {
            ObjectNode itemJson = objectMapper.createObjectNode();
            itemJson.put("position", i);
            itemJson.put("name", scrapeService.getItemName(i));
            itemJson.put("currentPrice", scrapeService.getItemPrice(i));
            itemJson.put("retailPrice",scrapeService.getItemRetailPrice(i));
            itemJson.put("image", scrapeService.getItemImage(i));
            itemJson.put("releaseDate", scrapeService.getItemReleaseDate(i));
            itemJson.put("info", scrapeService.getItemDescription(i));

            items.add(itemJson);

            // Break if we've reached the desired item count
            if (items.size() >= itemCount) {
                break;
            }
        }

        return items;
    }
}