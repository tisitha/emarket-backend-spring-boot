package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.model.OrderStatus;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto getOrder(UUID orderId, Authentication authentication);

    void addOrder(OrderRequestDto orderRequestDto, Authentication authentication);

    OrderPageSortDto getOrdersByVendor(OrderGetRequestDto orderGetRequestDto, Authentication authentication);

    OrderPageSortDto getOrdersByUser(OrderGetRequestDto orderGetRequestDto, Authentication authentication);

    OrderResponseDto changeOrderStatus(UUID orderId, Authentication authentication);

    OrderResponseDto deliveredOrder(UUID orderId);

    OrderResponseDto cancelOrder(UUID orderId, Authentication authentication);

}
