package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.config;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Bean
    public JacksonJsonMessageConverter messageConverter(){
        return new JacksonJsonMessageConverter();
    }
}
