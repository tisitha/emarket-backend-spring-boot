package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Province;
import com.tisitha.emarket.model.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountResponseDto {

    private UUID id;

    private String fname;

    private String lname;

    private String email;

    private String phoneNo;

    private String address;

    private Role role;

    private Province province;

    private String businessName;

    private String bankAccountNo;

    private String bank;
}
