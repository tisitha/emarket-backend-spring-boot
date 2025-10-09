package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {

    private UUID id;

    private VendorProfileDto vendorProfile;

    private String name;

    private String imgUrl;

    private String description;

    private Double price;

    private Double deal;

    private Boolean cod;

    private Boolean freeDelivery;

    private String brand;

    private CategoryResponseDto category;

    private Double avgRatings;

    private ProvinceResponseDto province;

    private WarrantyResponseDto warranty;

    private Integer quantity;

}
