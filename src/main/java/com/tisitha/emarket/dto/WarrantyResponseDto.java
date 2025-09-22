package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyResponseDto {

    private Long id;

    private String name;

    private List<Product> products;
}
