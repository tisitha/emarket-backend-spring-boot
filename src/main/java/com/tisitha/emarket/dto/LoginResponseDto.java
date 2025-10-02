package com.tisitha.emarket.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponseDto {

    UUID id;

    String name;

    String role;

    String token;
}
