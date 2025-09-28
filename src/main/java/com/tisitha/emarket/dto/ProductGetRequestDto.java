package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductGetRequestDto {

    @NotNull(message = "pageNumber is required")
    @PositiveOrZero(message = "pageNumber must be zero or a positive number")
    private Integer pageNumber;

    @NotNull(message = "pageSize is required")
    @Min(value = 1, message = "pageSize must be at least 1")
    private Integer pageSize;

    @Size(max = 50, message = "sortBy cannot exceed 50 characters")
    private String sortBy;

    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "dir must be either 'asc' or 'desc'")
    private String dir;

    @NotNull
    private Long categoryId;

    private boolean freeDelivery;

    private boolean cod;

    private List<Long> provinceIds;

    private List<Long> warrantyIds;

    private UUID vendorId;

    private double minPrice;

    private double maxPrice;

    private int minQuantity;

}
