package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDto {

    @NotBlank
    @Size(max = 250)
    private String name;

    private String description;

    @DecimalMin(value = "0.00", inclusive = true)
    @NotNull(message = "Price is required")
    private Double price;

    @DecimalMin(value = "0.00", inclusive = true)
    private Double deal;

    @NotNull
    private Boolean cod;

    @NotNull
    private Boolean freeDelivery;

    private String brand;

    @NotNull(message = "Category Id is required")
    private Long categoryId;

    @NotNull(message = "Province Id is required")
    private Long provinceId;

    @NotNull(message = "Warranty Id is required")
    private Long warrantyId;

    @NotNull(message = "Quantity is required")
    @Min(0)
    private Integer quantity;

}
