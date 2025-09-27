package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.NotificationPageDto;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface NotificationService {

    NotificationPageDto getNotificationOfUser(Integer pageSize, Authentication authentication);

    void markAsSeen(UUID notificationId,Authentication authentication);
}
