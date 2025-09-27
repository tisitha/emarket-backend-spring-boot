package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.OrderGetRequestDto;
import com.tisitha.emarket.dto.OrderPageSortDto;
import com.tisitha.emarket.dto.OrderRequestDto;
import com.tisitha.emarket.dto.OrderResponseDto;
import com.tisitha.emarket.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> addOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.addOrder(orderRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vendor")
    public  ResponseEntity<OrderPageSortDto> getOrdersByVendor(@RequestBody OrderGetRequestDto orderGetRequestDto) {
        return new ResponseEntity<>(orderService.getOrdersByVendor(orderGetRequestDto),HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<OrderPageSortDto> getOrdersByUser(@RequestBody OrderGetRequestDto orderGetRequestDto) {
        return new ResponseEntity<>(orderService.getOrdersByUser(orderGetRequestDto),HttpStatus.OK);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderResponseDto> ChangeOrderStatus(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.changeOrderStatus(orderId),HttpStatus.CREATED);
    }

    @PutMapping("/deliver/{orderId}")
    public  ResponseEntity<OrderResponseDto> deliveredOrder(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.deliveredOrder(orderId),HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{orderId}")
    public  ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.cancelOrder(orderId),HttpStatus.CREATED);
    }

}