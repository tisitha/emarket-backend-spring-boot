package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.OrderGetRequestDto;
import com.tisitha.emarket.dto.OrderPageSortDto;
import com.tisitha.emarket.dto.OrderRequestDto;
import com.tisitha.emarket.dto.OrderResponseDto;
import com.tisitha.emarket.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable UUID orderId, Authentication authentication) {
        return new ResponseEntity<>(orderService.getOrder(orderId,authentication), HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<Void> addOrder(@Valid @RequestBody OrderRequestDto orderRequestDto, Authentication authentication) {
        orderService.addOrder(orderRequestDto,authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order/vendor")
    public  ResponseEntity<OrderPageSortDto> getOrdersByVendor(@Valid @RequestBody OrderGetRequestDto orderGetRequestDto, Authentication authentication) {
        return new ResponseEntity<>(orderService.getOrdersByVendor(orderGetRequestDto,authentication),HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<OrderPageSortDto> getOrdersByUser(@Valid @RequestBody OrderGetRequestDto orderGetRequestDto, Authentication authentication) {
        return new ResponseEntity<>(orderService.getOrdersByUser(orderGetRequestDto,authentication),HttpStatus.OK);
    }

    @PutMapping("/order/update/{orderId}")
    public ResponseEntity<OrderResponseDto> ChangeOrderStatus(@PathVariable UUID orderId, Authentication authentication) {
        return new ResponseEntity<>(orderService.changeOrderStatus(orderId,authentication),HttpStatus.CREATED);
    }

    @PutMapping("/admin/order/deliver/{orderId}")
    public  ResponseEntity<OrderResponseDto> deliveredOrder(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.deliveredOrder(orderId),HttpStatus.CREATED);
    }

    @PutMapping("/order/cancel/{orderId}")
    public  ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable UUID orderId, Authentication authentication) {
        return new ResponseEntity<>(orderService.cancelOrder(orderId,authentication),HttpStatus.CREATED);
    }

}