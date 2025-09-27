package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.NotificationPageDto;
import com.tisitha.emarket.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{pageSize}")
    public ResponseEntity<NotificationPageDto> getNotificationOfUser(@PathVariable Integer pageSize, Authentication authentication) {
        return new ResponseEntity<>(notificationService.getNotificationOfUser(pageSize,authentication), HttpStatus.OK);
    }

    @PutMapping("/mark/{notificationId}")
    public ResponseEntity<Void> markAsSeen(@PathVariable UUID notificationId,Authentication authentication) {
        notificationService.markAsSeen(notificationId,authentication);
        return ResponseEntity.ok().build();
    }
}
