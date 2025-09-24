package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.OrderRequestDto;
import com.tisitha.emarket.dto.OrderResponseDto;
import com.tisitha.emarket.model.OrderStatus;
import com.tisitha.emarket.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.addOrder(orderRequestDto),HttpStatus.CREATED);

    }

    @GetMapping("/vendor/{vendorId}")
    public  ResponseEntity<List<OrderResponseDto>> getOrdersByVendor(@PathVariable UUID vendorId) {
        return new ResponseEntity<>(orderService.getOrdersByVendor(vendorId),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(orderService.getOrdersByUser(userId),HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public  ResponseEntity<OrderResponseDto> ChangeOrderStatus(@PathVariable UUID orderId,@RequestBody OrderStatus orderStatus) {
        return new ResponseEntity<>(orderService.ChangeOrderStatus(orderId,orderStatus),HttpStatus.CREATED);
    }

}
