package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;

import java.util.UUID;

public interface ProductService {

    ProductPageSortDto getProducts(ProductGetRequestDto productGetRequestDto);

    ProductPageSortDto getProductsByVendor(UUID vendorId,ProductGetRequestDto productGetRequestDto);

    ProductResponseDto getProduct(UUID id);

    ProductResponseDto addProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(UUID id,ProductRequestDto productRequestDto);

    void deleteProduct(UUID id);

}