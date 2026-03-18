package com.example.core.events;

import org.springframework.messaging.handler.annotation.Payload;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserCreatedEventsV1 {

    private String eventId;
    private String type;
    private LocalDateTime occurredAt;
    private Payload payload;


    public UserCreatedEventsV1(Payload payload){
        this.eventId = UUID.randomUUID().toString();
        this.type = "UserCreatedEventV1";
        this.occurredAt = LocalDateTime.now();
        this.payload = payload;
    }

    public static class Payload {
            private String userCode;
            private String email;
            private String name;
            private Integer idade;

        public Payload(String userCode, String email, String name, Integer idade) {
            this.userCode = userCode;
            this.email = email;
            this.name = name;
            this.idade = idade;
        }

        public String getUserCode(){
            return userCode;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public Integer getIdade() {
            return idade;
        }
    }

    public String getEventId() {
        return eventId;
    }
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public String getType() {
        return type;
    }
    public Payload getPayload() {
        return payload;
    }
}
