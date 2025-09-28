package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import com.tisitha.emarket.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/open/product")
    public ResponseEntity<ProductPageSortDto> getProducts(@Valid @RequestBody ProductGetRequestDto productGetRequestDto){
        return new ResponseEntity<>(productService.getProducts(productGetRequestDto), HttpStatus.OK);
    }

    @GetMapping("/product/vendor/{vendorId}")
    public ResponseEntity<ProductPageSortDto> getProductsByVendor(@PathVariable UUID vendorId,@Valid  @RequestBody ProductGetRequestDto productGetRequestDto){
        return new ResponseEntity<>(productService.getProductsByVendor(vendorId,productGetRequestDto), HttpStatus.OK);
    }

    @GetMapping("/open/product/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID productId){
        return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/open/search/{text}/{size}")
    public ResponseEntity<List<ProductResponseDto>> search(@PathVariable String text, @PathVariable Integer size){
        return new ResponseEntity<>(productService.search(text, size), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponseDto> addProducts(@Valid @RequestBody ProductRequestDto productRequestDto, Authentication authentication){
        return new ResponseEntity<>(productService.addProduct(productRequestDto,authentication), HttpStatus.CREATED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductResponseDto> updateProducts(@PathVariable UUID productId,@Valid @RequestBody ProductRequestDto productRequestDto,Authentication authentication){
        return new ResponseEntity<>(productService.updateProduct(productId,productRequestDto,authentication), HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable UUID productId,Authentication authentication){
        productService.deleteProduct(productId,authentication);
        return ResponseEntity.ok().build();
    }

}