package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;

    private String body;

    private Integer rate;

    private ProductResponseDto product;

    private Date date;

    private UserResponseDto user;

    private Boolean edited;
}
