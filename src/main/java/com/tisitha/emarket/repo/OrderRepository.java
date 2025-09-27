package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByVendorProfileVendorId(UUID vendorId, Pageable pageable);

    Page<Order> findAllByUserId(UUID userId,Pageable pageable);
}
