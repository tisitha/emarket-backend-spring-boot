package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductResponseDto> getProducts(ProductGetRequestDto productGetRequestDto);

    ProductResponseDto getProduct(UUID id);

    ProductResponseDto addProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(UUID id,ProductRequestDto productRequestDto);

    void deleteProduct(UUID id);

}