package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

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

    private UUID category;

    private boolean freeDelivery;

    private boolean cod;

    private UUID provinceId;

    private UUID warrantyId;

    private UUID vendorId;

}
