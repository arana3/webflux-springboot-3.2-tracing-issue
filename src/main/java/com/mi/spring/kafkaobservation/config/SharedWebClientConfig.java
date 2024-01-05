package com.mi.spring.kafkaobservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class SharedWebClientConfig {
    

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl("https://en7ak1fvq91vb.x.pipedream.net")
            .filter(logRequest())
            .filter(logResponse())
            .defaultHeader("source", "kafka-app-1")
            .build();
    }

    /**
	 * A filter function to log the request for each webclient call that's made by Auto-wiring the WebClient bean.
	 * @return - An object of ExchangeFilterFunction
	 */
	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			log.info("REQUEST::"+"Request Method: {} " + "Request URL: {} " + "Request Headers: {} ", clientRequest.method(),
				clientRequest.url(), clientRequest.headers().toString());
			return Mono.just(clientRequest);
		});
	}

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    log.info("RESPONSE::Headers - " + clientResponse.headers() + "::STATUS CODE - "+ clientResponse.statusCode());
                    return Mono.just(clientResponse);
                });
    }
}
