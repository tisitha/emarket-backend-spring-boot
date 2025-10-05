package com.tisitha.emarket.dto;

import java.util.List;

public record NotificationPageDto(
        List<NotificationResponseDto> notificationResponseDtoList,
        Integer newNotificationCount,
        boolean isLast
) {
}
