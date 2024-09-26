package com.example.selenium_kafka_service;

import com.example.selenium_kafka_service.config.WebDriverConfig;
import com.example.selenium_kafka_service.service.WebscrapeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class WebScrapeServiceTest {

    private WebDriver driver;

    @Autowired
    WebscrapeService webscrapeService;

    @Autowired
    WebDriverConfig configDriver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();

        webscrapeService = new WebscrapeService(driver);
    }

    @Test
    void testGetItemName() {
        webscrapeService.navigateToSearchPage("essentials");

        String itemName = webscrapeService.getItemName(1);
        String itemPrice = webscrapeService.getItemPrice(1);

        // TO-DO: fix the route to get the item description
        String itemDesc = webscrapeService.getItemDescription(1);

        assertNotNull(itemName);
        System.out.println("Item name: " + itemName);
        System.out.println("Item price: " + itemPrice);
        System.out.println("Item description: " + itemDesc);
    }
}
