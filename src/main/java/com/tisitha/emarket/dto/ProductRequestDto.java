package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDto {

    private UUID id;

    private UUID vendorProfileId;

    private String name;

    private String imgUrl;

    private String description;

    private double price;

    private double deal;

    private boolean cod;

    private boolean freeDelivery;

    private String brand;

    private Long categoryId;

    private Long provinceId;

    private Long warrantyId;

    private Integer quantity;

}
