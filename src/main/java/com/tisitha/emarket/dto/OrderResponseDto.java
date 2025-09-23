package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private UUID id;

    private User user;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private Product product;

    private VendorProfile vendorProfile;

    private Date date;

    private Integer quantity;

    private Double cost;

    private Double deliveryCost;

    private Double totalCost;


}
