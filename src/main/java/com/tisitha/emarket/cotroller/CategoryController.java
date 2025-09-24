package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.CategoryRequestDto;
import com.tisitha.emarket.dto.CategoryResponseDto;
import com.tisitha.emarket.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategoryTitles(), HttpStatus.OK);
    }

    @GetMapping("/root")
    public ResponseEntity<List<CategoryResponseDto>> getRootCategories(){
        return new ResponseEntity<>(categoryService.getRootCategoryTitles(), HttpStatus.OK);
    }

    @GetMapping("/sub/{id}")
    public ResponseEntity<List<CategoryResponseDto>> getSubCategories(@PathVariable Long parentId){
        return new ResponseEntity<>(categoryService.getSubCategoryTitles(parentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        return new ResponseEntity<>(categoryService.addCategoryTitle(categoryRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> editCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto categoryRequestDto){
        return new ResponseEntity<>(categoryService.updateCategoryTitle(categoryId,categoryRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategoryTitle(categoryId);
        return ResponseEntity.ok().build();
    }

}
