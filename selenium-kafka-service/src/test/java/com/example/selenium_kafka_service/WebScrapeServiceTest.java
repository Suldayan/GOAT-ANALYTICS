package com.example.selenium_kafka_service;

import com.example.selenium_kafka_service.config.WebDriverConfig;
import com.example.selenium_kafka_service.service.WebscrapeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
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
        driver = new EdgeDriver();

        webscrapeService = new WebscrapeService(driver);
    }

    @Test
    void testGetItemName() {
        webscrapeService.navigateToSearchPage("jordan 4 military black");

        String itemName = webscrapeService.getItemName(1);
        String itemPrice = webscrapeService.getItemPrice(1);
        String itemRetailPrice = webscrapeService.getItemRetailPrice(1);
        String itemLink = webscrapeService.getItemLink(1);
        String itemImg = webscrapeService.getItemImage(1);
        String itemReleaseDate = webscrapeService.getItemReleaseDate(1);

        // TO-DO: fix the route to get the item description
        String itemDesc = webscrapeService.getItemDescription(1);

        assertNotNull(itemName);
        System.out.println("Item name: " + itemName);
        System.out.println("Item price: " + itemPrice);
        System.out.println("Item retail price: " + itemRetailPrice);
        System.out.println("Item link: " + itemLink);
        System.out.println("Item Image: " + itemImg);
        System.out.println("Item Release Date: " + itemReleaseDate);
        System.out.println("Item description: " + itemDesc);
    }
}
