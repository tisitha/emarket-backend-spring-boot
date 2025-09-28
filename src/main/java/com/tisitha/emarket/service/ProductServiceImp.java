package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProductGetRequestDto;
import com.tisitha.emarket.dto.ProductPageSortDto;
import com.tisitha.emarket.dto.ProductRequestDto;
import com.tisitha.emarket.dto.ProductResponseDto;
import com.tisitha.emarket.exception.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
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
    private final SupabaseService supabaseService;

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
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return mapProductToProductDto(product);
    }

    @Override
    public List<ProductResponseDto> search(String text,int size) {
        Pageable pageable = PageRequest.of(0,size);
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(text,pageable);
        return products.getContent().stream().map(this::mapProductToProductDto).toList();
    }

    @Override
    public void updateProduct(UUID id,ProductRequestDto productRequestDto, MultipartFile file,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        VendorProfile vendorProfile = vendorProfileRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Optional<Category> category = categoryRepository.findById(productRequestDto.getCategoryId());
        Optional<Province> province = provinceRepository.findById(productRequestDto.getProvinceId());
        Optional<Warranty> warranty = warrantyRepository.findById(productRequestDto.getWarrantyId());
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        if(!product.getVendorProfile().equals(vendorProfile)){
            throw new UnauthorizeAccessException();
        }
        if(!file.isEmpty()){
            String imgUrl = supabaseService.upload(user.getId().toString(),file);
            product.setImgUrl(imgUrl);
        }
        Optional.ofNullable(productRequestDto.getName()).ifPresent(product::setName);
        Optional.ofNullable(productRequestDto.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(productRequestDto.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(productRequestDto.getDeal()).ifPresent(product::setDeal);
        Optional.ofNullable(productRequestDto.getCod()).ifPresent(product::setCod);
        Optional.ofNullable(productRequestDto.getFreeDelivery()).ifPresent(product::setFreeDelivery);
        Optional.ofNullable(productRequestDto.getBrand()).ifPresent(product::setBrand);
        category.ifPresent(product::setCategory);
        province.ifPresent(product::setProvince);
        warranty.ifPresent(product::setWarranty);
        productRepository.save(product);
    }

    @Override
    public void addProduct(@Valid ProductRequestDto productRequestDto, MultipartFile file, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        VendorProfile vendorProfile = vendorProfileRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Province province = provinceRepository.findById(productRequestDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
        Warranty warranty = warrantyRepository.findById(productRequestDto.getWarrantyId()).orElseThrow(WarrantyNotFoundException::new);
        Product product = new Product();
        if(!file.isEmpty()){
            String imgUrl = supabaseService.upload(user.getId().toString(),file);
            product.setImgUrl(imgUrl);
        }
        product.setVendorProfile(vendorProfile);
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDeal(productRequestDto.getDeal());
        product.setCod(productRequestDto.getCod());
        product.setFreeDelivery(productRequestDto.getFreeDelivery());
        product.setBrand(productRequestDto.getBrand());
        product.setCategory(category);
        product.setProvince(province);
        product.setWarranty(warranty);
        product.setQuantity(productRequestDto.getQuantity());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id,Authentication authentication) {
        Product product = productRepository.findByIdAndVendorProfileUserEmail(id,authentication.getName()).orElseThrow(UnauthorizeAccessException::new);
        supabaseService.deleteFile(product.getImgUrl());
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
}
