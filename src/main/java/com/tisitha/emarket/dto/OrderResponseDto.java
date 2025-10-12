package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.*;
import jakarta.persistence.Column;
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

    private UUID userId;

    private OrderStatus orderStatus;

    private String paymentMethodName;

    private UUID productId;

    private String productName;

    private Double cost;

    private UUID vendorId;

    private String vendorName;

    private Date date;

    private Integer quantity;

    private Double subTotalCost;

    private Double deliveryCost;

    private Double totalCost;
}
