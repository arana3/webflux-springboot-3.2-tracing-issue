package com.mi.spring.kafkaobservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = "com.mi.spring")
public class KafkaObservationApplication {

	public static void main(String[] args) {
		// No longer needed in Spring Boot 3.2
		// Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(KafkaObservationApplication.class, args);
	}

}
