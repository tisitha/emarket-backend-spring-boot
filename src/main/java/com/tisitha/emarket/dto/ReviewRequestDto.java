package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Product;
import com.tisitha.emarket.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    private UUID uid;

    private UUID pid;

    private String body;

    private Integer rate;

    private Product product;

    private User user;

}
