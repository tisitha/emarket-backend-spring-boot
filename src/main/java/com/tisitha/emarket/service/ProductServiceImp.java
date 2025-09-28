package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import com.tisitha.emarket.exception.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
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
        Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException());
        return mapProductToProductDto(product);
    }

    @Override
    public List<ProductResponseDto> search(String text,int size) {
        Pageable pageable = PageRequest.of(0,size);
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(text,pageable);
        return products.getContent().stream().map(this::mapProductToProductDto).toList();
    }

    @Override
    public ProductResponseDto updateProduct(UUID id,ProductRequestDto productRequestDto,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(user.getId()!=productRequestDto.getVendorProfileId()){
            throw new UnauthorizeAccessException();
        }
        VendorProfile vendorProfile = vendorProfileRepository.findById(productRequestDto.getVendorProfileId()).orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Province province = provinceRepository.findById(productRequestDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
        Warranty warranty = warrantyRepository.findById(productRequestDto.getWarrantyId()).orElseThrow(WarrantyNotFoundException::new);
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
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
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(user.getId()!=productRequestDto.getVendorProfileId()){
            throw new UnauthorizeAccessException();
        }
        Product newProduct = productRepository.save(mapProductDtoToProduct(productRequestDto));
        return mapProductToProductDto(newProduct);
    }

    @Override
    public void deleteProduct(UUID id,Authentication authentication) {
        productRepository.findByIdAndVendorProfileUserEmail(id,authentication.getName()).orElseThrow(UnauthorizeAccessException::new);
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
        VendorProfile vendorProfile = vendorProfileRepository.findById(productRequestDto.getVendorProfileId()).orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Province province = provinceRepository.findById(productRequestDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
        Warranty warranty = warrantyRepository.findById(productRequestDto.getWarrantyId()).orElseThrow(WarrantyNotFoundException::new);
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
