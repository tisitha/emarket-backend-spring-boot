package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorUpdateDto {

    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String fname;

    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lname;

    @Email(message = "Must be a valid email address")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String passwordRepeat;

    @Size(max = 20, message = "Contact number must not exceed 20 characters")
    @Pattern(regexp = "^(?=(?:[+\\-\\d]*\\d){9,})[+\\-\\d]+$",message = "Contact number must be a valid phone number")
    private String phoneNo;

    @Size(max = 250, message = "address cannot exceed 50 characters")
    private String address;

    private Long provinceId;

    @Size(max = 50, message = "Business name cannot exceed 50 characters")
    private String businessName;

    private String bankAccountNo;

    @Size(max = 50, message = "Bank name cannot exceed 50 characters")
    private String bank;

    @NotBlank(message = "Current Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String currentPassword;
}
