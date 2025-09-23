package com.tisitha.emarket.repo;

import com.tisitha.emarket.dto.CartItemResponseDto;
import com.tisitha.emarket.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findAllByUserId(UUID userId);
}
