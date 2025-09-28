package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductPageSortDto getProducts(ProductGetRequestDto productGetRequestDto);

    ProductPageSortDto getProductsByVendor(UUID vendorId, ProductGetRequestDto productGetRequestDto);

    ProductResponseDto getProduct(UUID id);

    List<ProductResponseDto> search(String text,int size);

    void addProduct(ProductRequestDto productRequestDto,Authentication authentication);

    void updateProduct(UUID id,ProductRequestDto productRequestDto,Authentication authentication);

    void deleteProduct(UUID id,Authentication authentication);

}