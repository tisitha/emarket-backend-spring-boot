package com.tisitha.emarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    @NotBlank(message = "Body is required")
    @Size(max = 1000, message = "Body cannot exceed 1000 characters")
    private String body;

    @NotNull(message = "Rate is required")
    private Integer rate;

    @NotNull(message = "Product Id is required")
    private UUID productId;

}
