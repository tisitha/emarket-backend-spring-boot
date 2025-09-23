package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByVendorProfileVendorId(UUID vendorId);

    List<Order> findAllByUserId(UUID userId);
}
