package com.panjuak.maintanance.services.impl;

import com.panjuak.maintanance.entities.FcmPayload;
import com.panjuak.maintanance.services.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Slf4j
public class FcmServiceImpl implements FcmService {
    @Value("${firebase.fcm.server-key}")
    private String fcmKeyServer;
    @Override
    public void sendNotificationByTopic(String topic, FcmPayload<?> payload) {
        RestTemplate restTemplate = new RestTemplate();

        HashMap<String, String> notification = new HashMap<>();

        notification.put("title", payload.getTitle());
        notification.put("body", payload.getBody());

        HashMap<String, Object> data = new HashMap<>();
        data.put("to", "/topics/" + topic);
        data.put("notification", notification);
        data.put("data", payload.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "key=" + fcmKeyServer);

        HttpEntity<HashMap<String, Object>> body  = new HttpEntity<>(data, headers);
        ResponseEntity<String> res = restTemplate.exchange(
                "https://fcm.googleapis.com/fcm/send",
                HttpMethod.POST,
                body,
                String.class
        );

        res.getStatusCode();
    }
}
