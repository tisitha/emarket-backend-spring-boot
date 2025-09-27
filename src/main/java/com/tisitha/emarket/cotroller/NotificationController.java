package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.NotificationGetRequestDto;
import com.tisitha.emarket.dto.NotificationPageDto;
import com.tisitha.emarket.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<NotificationPageDto> getNotificationOfUser(@RequestBody NotificationGetRequestDto notificationGetRequestDto) {
        return new ResponseEntity<>(notificationService.getNotificationOfUser(notificationGetRequestDto), HttpStatus.OK);
    }


    @PutMapping("/mark/{notificationId}")
    public ResponseEntity<Void> markAsSeen(@PathVariable UUID notificationId) {
        notificationService.markAsSeen(notificationId);
        return ResponseEntity.ok().build();
    }
}
