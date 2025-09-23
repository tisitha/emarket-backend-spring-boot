package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.OrderRequestDto;
import com.tisitha.emarket.dto.OrderResponseDto;
import com.tisitha.emarket.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto getOrder(UUID orderId);

    void addOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersByVendor(UUID vendorId);

    List<OrderResponseDto> getOrdersByUser(UUID userId);

    OrderResponseDto ChangeOrderStatus(UUID orderId, OrderStatus orderStatus);

}
