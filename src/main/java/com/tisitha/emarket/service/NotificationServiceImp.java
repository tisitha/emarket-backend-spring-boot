package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.NotificationGetRequestDto;
import com.tisitha.emarket.dto.NotificationPageDto;
import com.tisitha.emarket.dto.NotificationResponseDto;
import com.tisitha.emarket.model.Notification;
import com.tisitha.emarket.repo.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationServiceImp implements NotificationService{

    private final NotificationRepository notificationRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationPageDto getNotificationOfUser(NotificationGetRequestDto notificationGetRequestDto) {
        Sort sort = Sort.by("dateAndTime").descending();
        Pageable pageable = PageRequest.of(0,notificationGetRequestDto.getPageSize(),sort);
        Page<Notification> notifications = notificationRepository.findByUserId(notificationGetRequestDto.getUserId(),pageable);
        return new NotificationPageDto(notifications.getContent().stream().map(this::mapNotificationToDto).toList(),notifications.isLast());
    }

    @Override
    public void markAsSeen(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(()->new RuntimeException(""));
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    private NotificationResponseDto mapNotificationToDto(Notification notification){
        return new NotificationResponseDto(
                notification.getId(),
                notification.getMessage(),
                notification.isSeen(),
                notification.getDateAndTime()
        );
    }
}
