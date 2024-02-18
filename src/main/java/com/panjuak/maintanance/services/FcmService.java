package com.panjuak.maintanance.services;

import com.panjuak.maintanance.entities.FcmPayload;

public interface FcmService {
    void sendNotificationByTopic(String topic, FcmPayload<?> payload);
}
