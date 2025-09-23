package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CategoryRequestDto;
import com.tisitha.emarket.dto.CategoryResponseDto;
import com.tisitha.emarket.model.Category;
import com.tisitha.emarket.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDto> getCategoryTitles() {
        List<Category> warranties = categoryRepository.findAll();
        return warranties.stream().map(this::mapCategoryToCategoryDto).toList();
    }

    @Override
    public CategoryResponseDto getCategoryTitle(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException(""));
        return mapCategoryToCategoryDto(category);
    }

    @Override
    public CategoryResponseDto addCategoryTitle(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setParent(categoryRequestDto.getParent());
        Category newCategory = categoryRepository.save(category);
        return mapCategoryToCategoryDto(newCategory);
    }

    @Override
    public CategoryResponseDto updateCategoryTitle(Long categoryId,CategoryRequestDto categoryRequestDto) {
        Category oldCategory = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException(""));
        oldCategory.setName(categoryRequestDto.getName());
        oldCategory.setParent(categoryRequestDto.getParent());
        Category newCategory = categoryRepository.save(oldCategory);
        return mapCategoryToCategoryDto(newCategory);
    }

    @Override
    public void deleteCategoryTitle(Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException(""));
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryResponseDto> getRootCategoryTitles() {
        List<Category> categories = categoryRepository.findByParentIsNull();
        return categories.stream().map(this::mapCategoryToCategoryDto).toList();
    }

    @Override
    public List<CategoryResponseDto> getSubCategoryTitles(Long id) {
        List<Category> categories = categoryRepository.findByParentId(id);
        return categories.stream().map(this::mapCategoryToCategoryDto).toList();
    }

    private CategoryResponseDto mapCategoryToCategoryDto(Category category){
        return new CategoryResponseDto(category.getId(),category.getName(),category.getParent(),category.getChildren(),category.getProductList());
    }

}
