package com.tisitha.emarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserToVendorUpdateDto {

    @NotBlank(message = "Business name is required")
    @Size(max = 50, message = "Business name cannot exceed 50 characters")
    private String businessName;

    @NotBlank(message = "Bank account No. is required")
    private String bankAccountNo;

    @NotBlank(message = "Bank name is required")
    @Size(max = 50, message = "Bank name cannot exceed 50 characters")
    private String bank;

    @NotBlank(message = "Current Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String currentPassword;
}
