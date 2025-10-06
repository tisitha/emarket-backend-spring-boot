package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.CategoryRequestDto;
import com.tisitha.emarket.dto.CategoryResponseDto;
import com.tisitha.emarket.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/open/category")
    public ResponseEntity<List<CategoryResponseDto>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategoryTitles(), HttpStatus.OK);
    }

    @GetMapping("/open/category/root")
    public ResponseEntity<List<CategoryResponseDto>> getRootCategories(){
        return new ResponseEntity<>(categoryService.getRootCategoryTitles(), HttpStatus.OK);
    }

    @GetMapping("/open/category/sub/{parentId}")
    public ResponseEntity<List<CategoryResponseDto>> getSubCategories(@PathVariable Long parentId){
        return new ResponseEntity<>(categoryService.getSubCategoryTitles(parentId), HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto){
        return new ResponseEntity<>(categoryService.addCategoryTitle(categoryRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/{id}")
    public ResponseEntity<CategoryResponseDto> editCategory(@PathVariable Long categoryId,@RequestBody CategoryRequestDto categoryRequestDto){
        return new ResponseEntity<>(categoryService.updateCategoryTitle(categoryId,categoryRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategoryTitle(categoryId);
        return ResponseEntity.ok().build();
    }

}
