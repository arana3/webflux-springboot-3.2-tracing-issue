// package com.mi.spring.kafkaobservation.handler;

// import java.util.Map;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.support.KafkaHeaders;
// import org.springframework.messaging.handler.annotation.Header;
// import org.springframework.messaging.handler.annotation.Headers;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.stereotype.Component;
// import org.springframework.web.reactive.function.client.WebClient;

// import lombok.extern.slf4j.Slf4j;

// @Component
// @Slf4j
// public class KafkaConsumerHandler {
    
//     private WebClient webClient;

//     public KafkaConsumerHandler(WebClient webClient) {
//         this.webClient = webClient.mutate().baseUrl("https://enk87uigxc4ur.x.pipedream.net").build();
//     }

//     @KafkaListener(topics = {"test-topic"}, groupId = "ahmad-group")
//     public void consume(@Payload String messageValue, 
//         @Headers Map<String, Object> headers, 
//         @Header(KafkaHeaders.RECEIVED_KEY) String key) {

//             log.info(headers.toString());
//             log.debug(messageValue);

//             var msg = this.webClient.post().bodyValue(messageValue).header("received-key", key).retrieve().toBodilessEntity()
//                 .doOnSuccess(response -> {
//                     log.info("Sent kafka msg payload and got back: " + response.getHeaders());
                   
//                 })
//                 .block();
//             return;
//     }

// }
