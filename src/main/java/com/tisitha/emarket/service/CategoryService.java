package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CategoryRequestDto;
import com.tisitha.emarket.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getCategoryTitles();

    CategoryResponseDto getCategoryTitle(Long categoryMethodId);

    CategoryResponseDto addCategoryTitle(CategoryRequestDto categoryMethodRequestDto);

    CategoryResponseDto updateCategoryTitle(Long categoryMethodId,CategoryRequestDto categoryMethodRequestDto);

    void deleteCategoryTitle(Long categoryMethodId);

    List<CategoryResponseDto> getRootCategoryTitles();

    List<CategoryResponseDto> getSubCategoryTitles(Long id);
}
