package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Category;
import com.tisitha.emarket.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponseDto {

    private Long id;

    private String name;

    private Category parent;

    private Set<Category> children;

    private List<Product> productList;

}
