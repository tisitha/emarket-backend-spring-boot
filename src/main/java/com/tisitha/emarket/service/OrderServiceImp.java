package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ReviewPassRepository reviewPassRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ProductRepository productRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public OrderResponseDto getOrder(UUID orderId, Authentication authentication) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException(""));
        if(!order.getUser().getEmail().equals(authentication.getName()) && !order.getVendorProfile().getUser().getEmail().equals(authentication.getName())){
            throw new RuntimeException("");
        }
        return mapOrderToOrderDto(order);
    }

    @Override
    @Transactional
    public void addOrder(OrderRequestDto orderRequestDto, Authentication authentication) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(authentication.getName());
        for (CartItem cartItem: cartItems){
            Order order = new Order();
            order.setUser(cartItem.getUser());
            order.setOrderStatus(OrderStatus.PENDING);
            PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequestDto.getPaymentMethodId()).orElseThrow(()->new RuntimeException(""));
            order.setPaymentMethod(paymentMethod);
            Product product = cartItem.getProduct();
            if(cartItem.getQuantity()==0 || cartItem.getQuantity()> product.getQuantity()){
                cartItemRepository.deleteById(cartItem.getId());
                Notification notification = new Notification();
                notification.setSeen(false);
                notification.setUser(cartItem.getUser());
                notification.setNotificationType(NotificationType.PRODUCT);
                notification.setAttachedId(cartItem.getProduct().getId().toString());
                notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
                notification.setMessage(cartItem.getProduct().getName()+"\nItem is out of stock");
                notificationRepository.save(notification);
                notification.setUser(cartItem.getProduct().getVendorProfile().getUser());
                notificationRepository.save(notification);
                continue;
            }
            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            productRepository.save(product);
            order.setQuantity(cartItem.getQuantity());
            order.setProduct(product);
            order.setVendorProfile(cartItem.getProduct().getVendorProfile());
            order.setDate(new Date());
            double cost = cartItem.getProduct().getDeal()==0?cartItem.getProduct().getPrice()*cartItem.getQuantity():cartItem.getProduct().getDeal()*cartItem.getQuantity();
            order.setCost(cost);
            double deliveryCost = cartItem.getProduct().isFreeDelivery()?0.0:250.0;
            order.setDeliveryCost(deliveryCost);
            order.setTotalCost(cost+deliveryCost);
            orderRepository.save(order);
            cartItemRepository.deleteById(cartItem.getId());
        }
    }

    @Override
    public OrderPageSortDto getOrdersByVendor(OrderGetRequestDto orderGetRequestDto, Authentication authentication) {
        Sort sort = orderGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(orderGetRequestDto.getSortBy()).ascending():Sort.by(orderGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(orderGetRequestDto.getPageNumber(),orderGetRequestDto.getPageSize(),sort);
        Page<Order> orders = orderRepository.findAllByVendorProfileUserEmail(authentication.getName(),pageable);
        return new OrderPageSortDto(orders.getContent().stream().map(this::mapOrderToOrderDto).toList(),orders.getTotalElements(),orders.getTotalPages(),orders.isLast());
    }

    @Override
    public OrderPageSortDto getOrdersByUser(OrderGetRequestDto orderGetRequestDto, Authentication authentication) {
        Sort sort = orderGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(orderGetRequestDto.getSortBy()).ascending():Sort.by(orderGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(orderGetRequestDto.getPageNumber(),orderGetRequestDto.getPageSize(),sort);
        Page<Order> orders = orderRepository.findAllByUserEmail(authentication.getName(),pageable);
        return new OrderPageSortDto(orders.getContent().stream().map(this::mapOrderToOrderDto).toList(),orders.getTotalElements(),orders.getTotalPages(),orders.isLast());
    }

    @Override
    @Transactional
    public OrderResponseDto changeOrderStatus(UUID orderId, Authentication authentication) {
        Order order = orderRepository.findByIdAndVendorProfileUserEmail(authentication.getName()).orElseThrow(()->new RuntimeException(""));
        OrderStatus newOrderStatus;
        if(order.getOrderStatus()==OrderStatus.PENDING){
            newOrderStatus = OrderStatus.PROCESSING;
            Notification notification = new Notification();
            notification.setSeen(false);
            notification.setUser(order.getUser());
            notification.setNotificationType(NotificationType.ORDER);
            notification.setAttachedId(order.getId().toString());
            notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
            notification.setMessage("Order Received by the Seller\nSeller is preparing your order");
            notificationRepository.save(notification);
        }
        else if (order.getOrderStatus()==OrderStatus.PROCESSING) {
            newOrderStatus = OrderStatus.SHIPPED;
            Notification notification = new Notification();
            notification.setSeen(false);
            notification.setUser(order.getUser());
            notification.setNotificationType(NotificationType.ORDER);
            notification.setAttachedId(order.getId().toString());
            notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
            notification.setMessage("Order Reached at delivery service\nPackage will soon head to you.");
            notificationRepository.save(notification);
        }
        else{
            throw new RuntimeException("");
        }
        order.setOrderStatus(newOrderStatus);
        Order newOrder = orderRepository.save(order);
        return mapOrderToOrderDto(newOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto deliveredOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException(""));
        if(order.getOrderStatus()!=OrderStatus.SHIPPED){
            throw new RuntimeException("");
        }
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(order.getUser());
        notification.setNotificationType(NotificationType.ORDER);
        notification.setAttachedId(order.getId().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage("Delivered\nPackage delivered!");
        notificationRepository.save(notification);
        order.setOrderStatus(OrderStatus.DELIVERED);
        Order newOrder = orderRepository.save(order);
        ReviewPass reviewPass = new ReviewPass();
        reviewPass.setProduct(newOrder.getProduct());
        reviewPass.setUser(newOrder.getUser());
        reviewPassRepository.save(reviewPass);
        return mapOrderToOrderDto(newOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(UUID orderId, Authentication authentication) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException(""));
        if(order.getOrderStatus()==OrderStatus.CANCELLED || order.getOrderStatus()==OrderStatus.DELIVERED){
            throw new RuntimeException("");
        }
        if(!order.getUser().getEmail().equals(authentication.getName()) && !order.getVendorProfile().getUser().getEmail().equals(authentication.getName())){
            throw new RuntimeException("");
        }
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(order.getUser());
        notification.setNotificationType(NotificationType.ORDER);
        notification.setAttachedId(order.getId().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage("Your order is canceled!");
        notificationRepository.save(notification);
        order.setOrderStatus(OrderStatus.CANCELLED);
        Order newOrder = orderRepository.save(order);
        return mapOrderToOrderDto(newOrder);
    }

    private OrderResponseDto mapOrderToOrderDto(Order order){
        return new OrderResponseDto(order.getId(),order.getUser(),order.getOrderStatus(),order.getPaymentMethod(),order.getProduct(),order.getVendorProfile(),order.getDate(),order.getQuantity(),order.getCost(),order.getDeliveryCost(), order.getTotalCost());
    }
}
