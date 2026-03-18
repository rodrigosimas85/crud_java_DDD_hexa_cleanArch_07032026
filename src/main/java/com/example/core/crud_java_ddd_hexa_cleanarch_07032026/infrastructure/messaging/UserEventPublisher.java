package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.messaging;


import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.config.RabbitConfig;
import com.example.core.events.UserCreatedEventsV1;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisher (RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(UserCreatedEventsV1 event){
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );
    }

}
