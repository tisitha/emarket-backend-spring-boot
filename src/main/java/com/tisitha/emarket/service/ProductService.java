package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductPageSortDto getProducts(ProductGetRequestDto productGetRequestDto);

    List<ProductResponseDto> getDealProducts(int size);

    ProductPageSortDto getProductsByVendor(UUID vendorId, ProductGetRequestDto productGetRequestDto);

    ProductResponseDto getProduct(UUID id);

    List<ProductResponseDto> search(String text,int size);

    void addProduct(ProductRequestDto productRequestDto, MultipartFile file, Authentication authentication);

    void updateProduct(UUID id,ProductRequestDto productRequestDto, MultipartFile file,Authentication authentication);

    void deleteProduct(UUID id,Authentication authentication);

}