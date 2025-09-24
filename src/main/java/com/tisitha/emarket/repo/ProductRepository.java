package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByCategoryIdAndFreeDeliveryInAndCodInAndProvinceIdInAndWarrantyIdInAndPriceGreaterThanEqualAndPriceLessThanEqual(
            Long categoryId,
            List<Boolean> freeDeliveries,
            List<Boolean> cods,
            List<Long> provinces,
            List<Long> warranties,
            double minPrice,
            double maxPrice,
            Pageable pageable);

    Page<Product> findAllByCategoryIdInAndFreeDeliveryInAndCodInAndProvinceIdInAndWarrantyIdInAndPriceGreaterThanEqualAndPriceLessThanEqualAndVendorProfileVendorId(
            List<Long> categoryId,
            List<Boolean> freeDeliveries,
            List<Boolean> cods,
            List<Long> provinces,
            List<Long> warranties,
            double minPrice,
            double maxPrice,
            UUID vendorProfileId,
            Pageable pageable);
}
