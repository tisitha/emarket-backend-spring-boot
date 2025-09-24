package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import com.tisitha.emarket.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductPageSortDto> getProducts(@RequestBody ProductGetRequestDto productGetRequestDto){
        return new ResponseEntity<>(productService.getProducts(productGetRequestDto), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID productId){
        return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProducts(@RequestBody ProductRequestDto productRequestDto){
        return new ResponseEntity<>(productService.addProduct(productRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProducts(@PathVariable UUID productId,@RequestBody ProductRequestDto productRequestDto){
        return new ResponseEntity<>(productService.updateProduct(productId,productRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable UUID productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}