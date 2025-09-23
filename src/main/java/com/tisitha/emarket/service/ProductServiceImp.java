package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import com.tisitha.emarket.model.Product;
import com.tisitha.emarket.repo.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDto> getProducts(ProductGetRequestDto productGetRequestDto) {
        Sort sort = productGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(productGetRequestDto.getSortBy()).ascending():Sort.by(productGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(productGetRequestDto.getPageNumber(),productGetRequestDto.getPageSize(),sort);
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapProductToProductDto).toList();
    }

    @Override
    public ProductResponseDto getProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException());
        return mapProductToProductDto(product);
    }

    @Override
    public ProductResponseDto updateProduct(UUID id,ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException());
        product.setVendorProfile(productRequestDto.getVendorProfile());
        product.setName(productRequestDto.getName());
        product.setImgUrl(productRequestDto.getImgUrl());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDeal(productRequestDto.getDeal());
        product.setCod(productRequestDto.isCod());
        product.setFreeDelivery(productRequestDto.isFreeDelivery());
        product.setBrand(productRequestDto.getBrand());
        product.setCategory(productRequestDto.getCategory());
        product.setProvince(productRequestDto.getProvince());
        product.setWarranty(productRequestDto.getWarranty());
        Product newProduct = productRepository.save(product);
        return mapProductToProductDto(newProduct);
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        Product newProduct = productRepository.save(mapProductDtoToProduct(productRequestDto));
        return mapProductToProductDto(newProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.findById(id).orElseThrow(()->new RuntimeException());
        productRepository.deleteById(id);
    }

    private ProductResponseDto mapProductToProductDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getVendorProfile(),
                product.getName(),
                product.getImgUrl(),
                product.getDescription(),
                product.getPrice(),
                product.getDeal(),
                product.isCod(),
                product.isFreeDelivery(),
                product.getBrand(),
                product.getCategory(),
                product.getReviews(),
                product.getProvince(),
                product.getWarranty(),
                product.getQuestions(),
                product.getCartItems(),
                product.getQuantity()
        );
    }

    private Product mapProductDtoToProduct(ProductRequestDto productRequestDto){
        return new Product(
                productRequestDto.getId(),
                productRequestDto.getVendorProfile(),
                productRequestDto.getName(),
                productRequestDto.getImgUrl(),
                productRequestDto.getDescription(),
                productRequestDto.getPrice(),
                productRequestDto.getDeal(),
                productRequestDto.isCod(),
                productRequestDto.isFreeDelivery(),
                productRequestDto.getBrand(),
                productRequestDto.getCategory(),
                null,
                productRequestDto.getProvince(),
                productRequestDto.getWarranty(),
                null,
                null,
                productRequestDto.getQuantity(),
                null
        );
    }
}
