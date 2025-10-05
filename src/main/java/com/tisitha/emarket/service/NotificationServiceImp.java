package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.NotificationPageDto;
import com.tisitha.emarket.dto.NotificationResponseDto;
import com.tisitha.emarket.exception.NotificationNotFoundException;
import com.tisitha.emarket.model.Notification;
import com.tisitha.emarket.repo.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class NotificationServiceImp implements NotificationService{

    private final NotificationRepository notificationRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationPageDto getNotificationOfUser(Integer pageSize, Authentication authentication) {
        Sort sort = Sort.by("dateAndTime").descending();
        Pageable pageable = PageRequest.of(0,pageSize,sort);
        Page<Notification> notifications = notificationRepository.findByUserEmail(authentication.getName(),pageable);
        int newNotificationCount = notificationRepository.countBySeenAndUserEmail(false, authentication.getName());
        return new NotificationPageDto(notifications.getContent().stream().map(this::mapNotificationToDto).toList(),newNotificationCount,notifications.isLast());
    }

    @Override
    public void markAsSeen(UUID notificationId,Authentication authentication) {
        Notification notification = notificationRepository.findByIdAndUserEmail(notificationId,authentication.getName()).orElseThrow(NotificationNotFoundException::new);
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllUnseenAsSeenOfUser(Authentication authentication) {
        notificationRepository.markUnseenAsSeenOfUserEmail(authentication.getName());
    }

    private NotificationResponseDto mapNotificationToDto(Notification notification){
        return new NotificationResponseDto(
                notification.getId(),
                notification.getMessage(),
                notification.getAttachedId(),
                notification.getNotificationType(),
                notification.isSeen(),
                notification.getDateAndTime()
        );
    }
}
