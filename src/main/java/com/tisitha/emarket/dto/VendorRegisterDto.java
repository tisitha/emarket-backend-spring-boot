package com.tisitha.emarket.dto;

import lombok.Data;

@Data
public class VendorRegisterDto {

    private String fname;

    private String lname;

    private String email;

    private String password;

    private String passwordRepeat;

    private String phoneNo;

    private String address;

    private Long provinceId;

    private String businessName;

    private String bankAccountNo;

    private String bank;

}
