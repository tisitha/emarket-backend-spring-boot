package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private UUID userId;

    private PaymentMethod paymentMethod;


}
