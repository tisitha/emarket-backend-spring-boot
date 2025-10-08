package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private UUID id;

    private UserResponseDto user;

    private OrderStatus orderStatus;

    private PaymentMethodResponseDto paymentMethod;

    private ProductResponseDto product;

    private VendorProfileDto vendorProfile;

    private Date date;

    private Integer quantity;

    private Double cost;

    private Double deliveryCost;

    private Double totalCost;


}
