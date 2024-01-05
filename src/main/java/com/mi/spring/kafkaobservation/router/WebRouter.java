package com.mi.spring.kafkaobservation.router;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@Component
public class WebRouter {
    

    private KafkaTemplate kafkaTemplate;

    private WebClient webClient;

    private Tracer tracer;

    private WebRouter(WebClient webClient, Tracer tracer, KafkaTemplate kafkaTemplate) {
        this.webClient = webClient;
        this.tracer = tracer;
        this.kafkaTemplate = kafkaTemplate;
        // this.kafkaTemplate.setObservationEnabled(true);

    }

    @GetMapping(path = "test/{id}")
    public Mono<String> sendTestMessage(@PathVariable("id") String id) {

        log.info("This log stmt will not have correlation-id");

        var logMono = Mono.defer(() -> {
            log.info("This log stmt will have correlation-id");
            return Mono.just("logged");
        });
        
       


        // This works
        return logMono
            .flatMap(t -> {
                // ignore above
                CompletableFuture<SendResult> sentMsg = kafkaTemplate.send("test-topic-2", id, "some value");
                return Mono.fromFuture(sentMsg);
            })
            .flatMap(sendResult -> {
                    log.info("completed kafka send");
                    return Mono.just(sendResult.toString());
                });
        
    }

    /**
     * No correlation-id in trace context's baggage
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> send(ServerRequest serverRequest) {
        log.info("This log stmt will not have correlation-id:: ");
        
        return Mono.just(serverRequest)
                .flatMap(t -> {
                    log.info("Server Request:: ", serverRequest);
                    // ignore above
                    CompletableFuture<SendResult> sentMsg = kafkaTemplate.send("test-topic-2", "1234", "some value");
                    return Mono.fromFuture(sentMsg);
                })
                .flatMap(sendResult -> {
                    log.info("completed kafka send");
                    return ServerResponse.ok()
                            .bodyValue("Kafka details ---> *topic name : " + "test-topic-2" + " *result : " + sendResult);
                });

    }

}
