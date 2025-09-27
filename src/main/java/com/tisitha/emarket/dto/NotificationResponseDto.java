package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NotificationResponseDto {

    private UUID id;

    private String message;

    private boolean seen;

    private LocalDateTime dateAndTime;

}
