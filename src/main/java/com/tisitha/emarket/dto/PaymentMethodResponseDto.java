package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentMethodResponseDto {

    private Long id;

    private String name;

    private List<Order> orders;
}
