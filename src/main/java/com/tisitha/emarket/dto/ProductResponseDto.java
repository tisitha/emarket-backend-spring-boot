package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    private List<ReviewResponseDto> reviews;

    private Double avgRatings;

    private ProvinceResponseDto province;

    private WarrantyResponseDto warranty;

    private List<QuestionResponseDto> questions;

    private Integer quantity;

}
