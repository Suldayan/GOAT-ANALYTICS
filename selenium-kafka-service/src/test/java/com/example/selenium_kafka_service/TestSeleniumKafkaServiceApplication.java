package com.example.selenium_kafka_service;

import org.springframework.boot.SpringApplication;

public class TestSeleniumKafkaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(SeleniumKafkaServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
