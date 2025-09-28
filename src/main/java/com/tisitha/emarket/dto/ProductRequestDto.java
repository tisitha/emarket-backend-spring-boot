package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDto {

    @NotNull(message = "Vendor Id is required")
    private UUID vendorProfileId;

    @NotBlank
    @Size(max = 250)
    private String name;

    private String imgUrl;

    private String description;

    @DecimalMin(value = "0.00", inclusive = true)
    @NotNull(message = "Price is required")
    private double price;

    @DecimalMin(value = "0.00", inclusive = true)
    private double deal;

    private boolean cod;

    private boolean freeDelivery;

    private String brand;

    @NotNull(message = "Category Id is required")
    private Long categoryId;

    @NotNull(message = "Province Id is required")
    private Long provinceId;

    @NotNull(message = "Warranty Id is required")
    private Long warrantyId;

    @NotNull
    @Min(0)
    private Integer quantity;

}
