package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j

public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url:http://localhost:8081/api/v1}")

    private String notificationServiceUrl;

    public void sendUserCreatedNotification(String userCode, String email){
        try{
            Map<String, Object> request = new HashMap<>();
            request.put("userId", userCode);
            request.put("type", "USER_CREATED");
            request.put("message", "Usuário com e-mail " + email + " e código " + userCode + " foi criado com sucesso");

            ResponseEntity<Void> response = restTemplate.postForEntity(
                    notificationServiceUrl + "/notification",
                    request,
                    Void.class
            );

            log.info("Notificação enviada para o usuário {}. Status: {}", userCode, response.getStatusCode());
        } catch (Exception e) {
            log.error("Falha ao enviar a notificação para o usuário {}: {}", userCode, e.getMessage());
            //throw new RuntimeException(e);
        }
    }

}
