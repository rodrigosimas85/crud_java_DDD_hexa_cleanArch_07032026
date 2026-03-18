package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "user.events.exchange";
    public static final String QUEUE = "user.events.queue";
    public static final String ROUTING_KEY = "user.created";


    @Bean
    public TopicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(ROUTING_KEY);
    }


}
