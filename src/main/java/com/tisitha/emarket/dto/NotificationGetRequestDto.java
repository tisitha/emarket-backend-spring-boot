package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationGetRequestDto {

    @NotNull(message = "pageSize is required")
    @Min(value = 1, message = "pageSize must be at least 1")
    private Integer pageSize;

    private UUID userId;

}
