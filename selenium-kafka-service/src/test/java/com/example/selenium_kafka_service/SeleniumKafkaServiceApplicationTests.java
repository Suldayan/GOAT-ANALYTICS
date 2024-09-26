package com.example.selenium_kafka_service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SeleniumKafkaServiceApplicationTests {
	
	private WebDriver webDriver;
	
	@BeforeAll
	public static void setupWebdriver() {

	}

	@Test
	void contextLoads() {
	}

}
