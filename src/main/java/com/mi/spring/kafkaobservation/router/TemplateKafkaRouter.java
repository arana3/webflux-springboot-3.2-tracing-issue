package com.mi.spring.kafkaobservation.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class TemplateKafkaRouter {

	
	@Bean
	public RouterFunction<ServerResponse> defaultProducerRoute(WebRouter webRouter ) {
		
		return RouterFunctions.route(RequestPredicates.GET("/testme"), webRouter::send);

	}
}
