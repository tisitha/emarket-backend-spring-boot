package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequestDto {

    private String name;

    private Long parentId;

}
