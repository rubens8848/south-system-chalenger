package br.com.forja.bits.south.Consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AgendaConsumer {

    @KafkaListener(topics = "agenda", groupId = "agenda-group-id")
    void consume(String agenda){
        System.out.println("closed agenda - " + agenda);
    }
}
