package com.example.selenium_kafka_service.config;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("!gird")
@Lazy
@Configuration
public class WebDriverConfig {
    @ConditionalOnProperty(name = "browser", havingValue = "firefox")
    @Primary
    public WebDriver firefoxDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
        firefoxOptions.setCapability("proxy", proxy);
        return new FirefoxDriver(firefoxOptions);
    }

    @ConditionalOnProperty(name = "browser", havingValue = "chrome")
    @Primary
    public WebDriver chromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
        chromeOptions.setCapability("proxy", proxy);
        return new ChromeDriver(chromeOptions);
    }

    @ConditionalOnProperty(name = "browser", havingValue = "edge")
    @Primary
    public WebDriver edgeDriver() {
        EdgeOptions edgeOptions = new EdgeOptions();
        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
        edgeOptions.setCapability("proxy", proxy);
        return new EdgeDriver(edgeOptions);
    }

    @ConditionalOnProperty(name = "browser", havingValue = "safari")
    @Primary
    public WebDriver safariDriver() {
        SafariOptions safariOptions = new SafariOptions();
        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
        safariOptions.setCapability("proxy", proxy);
        return new SafariDriver(safariOptions);
    }
}
