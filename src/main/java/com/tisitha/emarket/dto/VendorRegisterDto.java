package com.tisitha.emarket.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorRegisterDto {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String fname;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lname;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String passwordRepeat;

    @NotBlank(message = "Contact No is required")
    @Size(max = 20, message = "Contact number must not exceed 20 characters")
    @Pattern(regexp = "^(?=(?:[+\\-\\d]*\\d){9,})[+\\-\\d]+$",message = "Contact number must be a valid phone number")
    private String phoneNo;

    @NotBlank(message = "Address is required")
    @Size(max = 250, message = "address cannot exceed 50 characters")
    private String address;

    @NotNull(message = "Province Id is required")
    private Long provinceId;

    @NotBlank(message = "Business name is required")
    @Size(max = 50, message = "Business name cannot exceed 50 characters")
    private String businessName;

    @NotBlank(message = "Bank account No. is required")
    private String bankAccountNo;

    @NotBlank(message = "Bank name is required")
    @Size(max = 50, message = "Bank name cannot exceed 50 characters")
    private String bank;

}
