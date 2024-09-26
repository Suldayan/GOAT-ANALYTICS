package com.example.selenium_kafka_service;

import com.example.selenium_kafka_service.service.ItemPageService;
import com.example.selenium_kafka_service.service.WebscrapeService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
class ItemPageServiceTest {

    @InjectMocks
    private ItemPageService itemPageService;

    @Mock
    WebscrapeService webscrapeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testScrapeItems() {
        when(webscrapeService.getItemName(1)).thenReturn("Test Item 1");
        when(webscrapeService.getItemPrice(1)).thenReturn("$65");
        when(webscrapeService.getItemRetailPrice(1)).thenReturn("$150");
        when(webscrapeService.getItemImage(1)).thenReturn("Item image");
        when(webscrapeService.getItemReleaseDate(1)).thenReturn("2024-09-24");
        when(webscrapeService.getItemDescription(1)).thenReturn("A unit test for the item page service");

        List<ObjectNode> result = itemPageService.scrapeItems("test search", 1);

        assertEquals(1, result.size());
        assertEquals("Test Item 1", result.getFirst().get("name").asText());
        assertEquals("$65", result.getFirst().get("currentPrice").asText());
    }
}
