package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Product;
import com.tisitha.emarket.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private UUID id;

    private String body;

    private Integer rate;

    private Date date;

    private Product product;

    private User user;

    private Boolean edited;
}
