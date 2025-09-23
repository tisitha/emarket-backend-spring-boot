package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.OrderRequestDto;
import com.tisitha.emarket.dto.OrderResponseDto;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.CartItemRepository;
import com.tisitha.emarket.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService{

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderServiceImp(OrderRepository orderRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public OrderResponseDto getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException(""));
        return mapOrderToOrderDto(order);
    }

    @Override
    public void addOrder(OrderRequestDto orderRequestDto) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(orderRequestDto.getUserId());
        for (CartItem cartItem: cartItems){
            Order order = new Order();
            order.setUser(cartItem.getUser());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPaymentMethod(orderRequestDto.getPaymentMethod());
            order.setProduct(cartItem.getProduct());
            order.setVendorProfile(cartItem.getProduct().getVendorProfile());
            order.setDate(new Date());
            order.setQuantity(cartItem.getQuantity());
            double cost = cartItem.getProduct().getDeal()==0?cartItem.getProduct().getPrice()*cartItem.getQuantity():cartItem.getProduct().getDeal()*cartItem.getQuantity();
            order.setCost(cost);
            double deliveryCost = cartItem.getProduct().isFreeDelivery()?0.0:250.0;
            order.setDeliveryCost(deliveryCost);
            order.setTotalCost(cost+deliveryCost);
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderResponseDto> getOrdersByVendor(UUID vendorId) {
        List<Order> orders = orderRepository.findAllByVendorProfileVendorId(vendorId);
        return orders.stream().map(this::mapOrderToOrderDto).toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(UUID userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream().map(this::mapOrderToOrderDto).toList();
    }

    @Override
    public OrderResponseDto ChangeOrderStatus(UUID orderId,OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException(""));
        order.setOrderStatus(orderStatus);
        Order newOrder = orderRepository.save(order);
        return mapOrderToOrderDto(newOrder);
    }

    private OrderResponseDto mapOrderToOrderDto(Order order){
        return new OrderResponseDto(order.getId(),order.getUser(),order.getOrderStatus(),order.getPaymentMethod(),order.getProduct(),order.getVendorProfile(),order.getDate(),order.getQuantity(),order.getCost(),order.getDeliveryCost(), order.getTotalCost());
    }
}
