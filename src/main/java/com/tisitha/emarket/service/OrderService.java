package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto getOrder(UUID orderId);

    void addOrder(OrderRequestDto orderRequestDto);

    OrderPageSortDto getOrdersByVendor(OrderGetRequestDto orderGetRequestDto);

    OrderPageSortDto getOrdersByUser(OrderGetRequestDto orderGetRequestDto);

    OrderResponseDto changeOrderStatus(UUID orderId);

    OrderResponseDto deliveredOrder(UUID orderId);

    OrderResponseDto cancelOrder(UUID orderId);

}
