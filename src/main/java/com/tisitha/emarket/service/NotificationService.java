package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.NotificationGetRequestDto;
import com.tisitha.emarket.dto.NotificationPageDto;

import java.util.UUID;

public interface NotificationService {

    NotificationPageDto getNotificationOfUser(NotificationGetRequestDto notificationGetRequestDto);

    void markAsSeen(UUID notificationId);
}
