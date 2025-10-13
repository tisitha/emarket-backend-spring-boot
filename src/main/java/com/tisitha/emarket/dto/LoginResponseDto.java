package com.tisitha.emarket.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponseDto {

    private UUID id;

    private String name;

    private String role;

    private String token;

    private String email;
}
