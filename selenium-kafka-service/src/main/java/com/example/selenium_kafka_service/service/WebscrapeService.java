package com.example.selenium_kafka_service.service;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WebscrapeService {
    private static final Logger logger = LoggerFactory.getLogger(WebscrapeService.class);
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public WebscrapeService(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofMillis(5000));
    }

    public String getSearchUrl(String searchQuery) {
        String baseUrl = "https://www.goat.com/en-ca";
        String queryDash = "/search?query=";
        return baseUrl + queryDash + searchQuery;
    }

    private WebElement getItemElement(int position) {
        String selector = String.format("[data-grid-cell-position='%d']", position);
        WebElement itemElement = webDriver.findElement(By.cssSelector(selector));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", itemElement);
        return itemElement;
    }

    public String getItemName(int position) {
        try {
            WebElement itemElement = getItemElement(position);
            WebElement nameElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(itemElement, By.cssSelector("[data-qa='grid_cell_product_name']")));
            return nameElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get name for item at position {}: {}", position, e.getMessage());
            return "N/A";
        }
    }

    public String getItemPrice(int position) {
        try {
            WebElement itemElement = getItemElement(position);
            WebElement priceElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(itemElement, By.cssSelector("[data-qa='grid_cell_product_price'] .LocalizedCurrency__Amount-sc-yoa0om-0")));
            return priceElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get item price at position {}: {}", position, e.getMessage());
            return "Price not available";
        }
    }

    public String getItemRetailPrice(int position) {
        try {
            WebElement itemElement = getItemElement(position);
            WebElement retailPriceElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(itemElement, By.cssSelector("[data-qa='grid_cell_retail_price'] .LocalizedCurrency__Amount-sc-yoa0om-0")));
            return retailPriceElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get item retail price at position {}: {}", position, e.getMessage());
            return "Retail price not available";
        }
    }

    public String getItemImage(int position) {
        try {
            WebElement itemElement = getItemElement(position);
            WebElement imageElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(itemElement, By.tagName("img")));
            return imageElement.getAttribute("src");
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get item image at position {}: {}", position, e.getMessage());
            return "Item image currently N/A";
        }
    }

    public String getItemReleaseDate(int position) {
        try {
            WebElement itemElement = getItemElement(position);
            WebElement releaseDateElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(itemElement, By.cssSelector("[data-qa='grid_cell_product_release_date']")));
            return releaseDateElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get item release date at position {}: {}", position, e.getMessage());
            return "Release date currently N/A";
        }
    }

    public String getItemLink(int position) {
        try {
            WebElement itemElement = getItemElement(position);
            WebElement itemLinkElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(itemElement, By.tagName("a")));
            return itemLinkElement.getAttribute("href");
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get item link at position {}: {}", position, e.getMessage());
            return "Item link currently N/A";
        }
    }

    public String getItemDescription(int position) {
        String itemLink = getItemLink(position);
        String currentUrl = webDriver.getCurrentUrl();
        try {
            webDriver.get(itemLink);
            WebElement itemDescription = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa='product_description']")));
            return itemDescription.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.warn("Failed to get description for item at position {}: {}", position, e.getMessage());
            return "N/A";
        } finally {
            webDriver.get(currentUrl); // Return to the original page
        }
    }

    public void navigateToSearchPage(String searchQuery) {
        webDriver.get(getSearchUrl(searchQuery));
    }
}