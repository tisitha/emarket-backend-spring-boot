package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final WarrantyRepository warrantyRepository;
    private final CategoryRepository categoryRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public ProductPageSortDto getProducts(ProductGetRequestDto productGetRequestDto) {
        Sort sort = productGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(productGetRequestDto.getSortBy()).ascending():Sort.by(productGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(productGetRequestDto.getPageNumber(),productGetRequestDto.getPageSize(),sort);
        Page<Product> productsPage = productRepository.findAllByCategoryIdAndFreeDeliveryInAndCodInAndProvinceIdInAndWarrantyIdInAndPriceGreaterThanEqualAndPriceLessThanEqualAndQuantityGreaterThanEqual(
                productGetRequestDto.getCategoryId(),
                productGetRequestDto.isFreeDelivery()?List.of(true):List.of(true,false),
                productGetRequestDto.isCod()?List.of(true):List.of(true,false),
                productGetRequestDto.getProvinceIds(),
                productGetRequestDto.getWarrantyIds(),
                productGetRequestDto.getMinPrice(),
                productGetRequestDto.getMaxPrice(),
                productGetRequestDto.getMinQuantity(),
                pageable
        );
        List<Product> products = productsPage.getContent();
        return new ProductPageSortDto(
                products.stream().map(this::mapProductToProductDto).toList(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.isLast()
        );
    }

    @Override
    public ProductPageSortDto getProductsByVendor(UUID vendorId, ProductGetRequestDto productGetRequestDto) {
        Sort sort = productGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(productGetRequestDto.getSortBy()).ascending():Sort.by(productGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(productGetRequestDto.getPageNumber(),productGetRequestDto.getPageSize(),sort);
        Page<Product> productsPage = productRepository.findAllByFreeDeliveryInAndCodInAndProvinceIdInAndWarrantyIdInAndPriceGreaterThanEqualAndPriceLessThanEqualAndQuantityGreaterThanEqualAndVendorProfileVendorId(
                productGetRequestDto.isFreeDelivery()?List.of(true):List.of(true,false),
                productGetRequestDto.isCod()?List.of(true):List.of(true,false),
                productGetRequestDto.getProvinceIds(),
                productGetRequestDto.getWarrantyIds(),
                productGetRequestDto.getMinPrice(),
                productGetRequestDto.getMaxPrice(),
                productGetRequestDto.getMinQuantity(),
                vendorId,
                pageable
        );
        List<Product> products = productsPage.getContent();
        return new ProductPageSortDto(
                products.stream().map(this::mapProductToProductDto).toList(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.isLast()
        );
    }

    @Override
    public ProductResponseDto getProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException());
        return mapProductToProductDto(product);
    }

    @Override
    public ProductResponseDto updateProduct(UUID id,ProductRequestDto productRequestDto) {
        VendorProfile vendorProfile = vendorProfileRepository.findById(productRequestDto.getVendorProfileId()).orElseThrow(()->new RuntimeException());
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(()->new RuntimeException());
        Province province = provinceRepository.findById(productRequestDto.getProvinceId()).orElseThrow(()->new RuntimeException());
        Warranty warranty = warrantyRepository.findById(productRequestDto.getWarrantyId()).orElseThrow(()->new RuntimeException());
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException());
        product.setVendorProfile(vendorProfile);
        product.setName(productRequestDto.getName());
        product.setImgUrl(productRequestDto.getImgUrl());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDeal(productRequestDto.getDeal());
        product.setCod(productRequestDto.isCod());
        product.setFreeDelivery(productRequestDto.isFreeDelivery());
        product.setBrand(productRequestDto.getBrand());
        product.setCategory(category);
        product.setProvince(province);
        product.setWarranty(warranty);
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
        OptionalDouble average = product.getReviews().stream()
                .mapToInt(Review::getRate)
                .average();
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
                average.isPresent()?average.getAsDouble():null,
                product.getProvince(),
                product.getWarranty(),
                product.getQuestions(),
                product.getCartItems(),
                product.getQuantity()
        );
    }

    private Product mapProductDtoToProduct(ProductRequestDto productRequestDto){
        VendorProfile vendorProfile = vendorProfileRepository.findById(productRequestDto.getVendorProfileId()).orElseThrow(()->new RuntimeException());
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(()->new RuntimeException());
        Province province = provinceRepository.findById(productRequestDto.getProvinceId()).orElseThrow(()->new RuntimeException());
        Warranty warranty = warrantyRepository.findById(productRequestDto.getWarrantyId()).orElseThrow(()->new RuntimeException());
        Product product = new Product();
        product.setVendorProfile(vendorProfile);
        product.setName(productRequestDto.getName());
        product.setImgUrl(productRequestDto.getImgUrl());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDeal(productRequestDto.getDeal());
        product.setCod(productRequestDto.isCod());
        product.setFreeDelivery(productRequestDto.isFreeDelivery());
        product.setBrand(productRequestDto.getBrand());
        product.setCategory(category);
        product.setProvince(province);
        product.setWarranty(warranty);
        product.setQuantity(productRequestDto.getQuantity());
        return product;
    }
}
