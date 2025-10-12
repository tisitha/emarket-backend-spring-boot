package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.exception.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import com.tisitha.emarket.util.ObjectConverter;
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
    private final SiteConfigRepository siteConfigRepository;
    private final UserRepository userRepository;

    @Override
    public OrderResponseDto getOrder(UUID orderId, Authentication authentication) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        User user = (User) authentication.getPrincipal();
        if(!order.getUserId().equals(user.getId()) && !order.getVendorId().equals(user.getId())){
            throw new UnauthorizeAccessException();
        }
        return ObjectConverter.mapOrderToOrderDto(order);
    }

    @Override
    @Transactional
    public void addOrder(OrderRequestDto orderRequestDto, Authentication authentication) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(authentication.getName());
        for (CartItem cartItem: cartItems){
            Order order = new Order();
            order.setUserId(cartItem.getUser().getId());
            order.setOrderStatus(OrderStatus.PENDING);
            PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequestDto.getPaymentMethodId()).orElseThrow(PaymentMethodNotFoundException::new);
            order.setPaymentMethodName(paymentMethod.getName());
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
                throw new ProductOutOfStockException();
            }
            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            productRepository.save(product);
            order.setQuantity(cartItem.getQuantity());
            order.setProductId(product.getId());
            order.setProductName(product.getName());
            order.setCost(product.getDeal()==0?product.getPrice():product.getDeal());
            order.setVendorId(cartItem.getProduct().getVendorProfile().getVendorId());
            order.setVendorName(cartItem.getProduct().getVendorProfile().getBusinessName());
            order.setDate(new Date());
            double subTotalCost = cartItem.getProduct().getDeal()==0?cartItem.getProduct().getPrice()*cartItem.getQuantity():cartItem.getProduct().getDeal()*cartItem.getQuantity();
            order.setSubTotalCost(subTotalCost);
            double deliveryCost = cartItem.getProduct().isFreeDelivery()?0.0:Double.parseDouble(siteConfigRepository.findByName(SiteConfigName.DELIVERY_COST.name()).get().getValue());
            order.setDeliveryCost(deliveryCost);
            order.setTotalCost(subTotalCost+deliveryCost);
            orderRepository.save(order);
            cartItemRepository.deleteById(cartItem.getId());
        }
    }

    @Override
    public OrderPageSortDto getOrdersByVendor(OrderGetRequestDto orderGetRequestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Sort sort = orderGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(orderGetRequestDto.getSortBy()).ascending():Sort.by(orderGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(orderGetRequestDto.getPageNumber(),orderGetRequestDto.getPageSize(),sort);
        Page<Order> orders = orderRepository.findAllByVendorId(user.getId(),pageable);
        return new OrderPageSortDto(orders.getContent().stream().map(ObjectConverter::mapOrderToOrderDto).toList(),orders.getTotalElements(),orders.getTotalPages(),orders.isLast());
    }

    @Override
    public OrderPageSortDto getOrdersByUser(OrderGetRequestDto orderGetRequestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Sort sort = orderGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(orderGetRequestDto.getSortBy()).ascending():Sort.by(orderGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(orderGetRequestDto.getPageNumber(),orderGetRequestDto.getPageSize(),sort);
        Page<Order> orders = orderRepository.findAllByUserId(user.getId(),pageable);
        return new OrderPageSortDto(orders.getContent().stream().map(ObjectConverter::mapOrderToOrderDto).toList(),orders.getTotalElements(),orders.getTotalPages(),orders.isLast());
    }

    @Override
    @Transactional
    public OrderResponseDto changeOrderStatus(UUID orderId, Authentication authentication) {
        User authenticator = (User) authentication.getPrincipal();
        Order order = orderRepository.findByIdAndVendorId(orderId,authenticator.getId()).orElseThrow(OrderNotFoundException::new);
        User user = userRepository.findById(order.getUserId()).orElseThrow(UserNotFoundException::new);
        OrderStatus newOrderStatus;
        if(order.getOrderStatus()==OrderStatus.PENDING){
            newOrderStatus = OrderStatus.PROCESSING;
            Notification notification = new Notification();
            notification.setSeen(false);
            notification.setUser(user);
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
            notification.setUser(user);
            notification.setNotificationType(NotificationType.ORDER);
            notification.setAttachedId(order.getId().toString());
            notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
            notification.setMessage("Order Reached at delivery service\nPackage will soon head to you.");
            notificationRepository.save(notification);
        }
        else{
            throw new InvalidInputException();
        }
        order.setOrderStatus(newOrderStatus);
        Order newOrder = orderRepository.save(order);
        return ObjectConverter.mapOrderToOrderDto(newOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto deliveredOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if(order.getOrderStatus()!=OrderStatus.SHIPPED){
            throw new InvalidInputException();
        }
        User user = userRepository.findById(order.getUserId()).orElseThrow(UserNotFoundException::new);
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(user);
        notification.setNotificationType(NotificationType.ORDER);
        notification.setAttachedId(order.getId().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage("Delivered\nPackage delivered!");
        notificationRepository.save(notification);
        order.setOrderStatus(OrderStatus.DELIVERED);
        Order newOrder = orderRepository.save(order);
        ReviewPass reviewPass = new ReviewPass();
        Product product = productRepository.findById(newOrder.getProductId()).orElseThrow(ProductNotFoundException::new);
        reviewPass.setProduct(product);
        reviewPass.setUser(user);
        reviewPassRepository.save(reviewPass);
        return ObjectConverter.mapOrderToOrderDto(newOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(UUID orderId, Authentication authentication) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        User Authenticator = (User) authentication.getPrincipal();
        if(order.getOrderStatus()==OrderStatus.CANCELLED || order.getOrderStatus()==OrderStatus.DELIVERED){
            throw new InvalidInputException();
        }
        if(!order.getUserId().equals(Authenticator.getId()) && !order.getVendorId().equals(Authenticator.getId())){
            throw new UnauthorizeAccessException();
        }
        User user = userRepository.findById(order.getUserId()).orElseThrow(UserNotFoundException::new);
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(user);
        notification.setNotificationType(NotificationType.ORDER);
        notification.setAttachedId(order.getId().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage("Your order is canceled!");
        notificationRepository.save(notification);
        order.setOrderStatus(OrderStatus.CANCELLED);
        Order newOrder = orderRepository.save(order);
        return ObjectConverter.mapOrderToOrderDto(newOrder);
    }

}
