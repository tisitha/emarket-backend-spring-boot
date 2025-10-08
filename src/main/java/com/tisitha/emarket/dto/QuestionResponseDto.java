package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {

    private Long id;

    private String question;

    private String answer;

    private Product product;

    private UserResponseDto user;

    private Date date;

}
