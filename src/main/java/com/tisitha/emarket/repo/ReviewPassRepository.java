package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.ReviewPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewPassRepository extends JpaRepository<ReviewPass, UUID> {

    Boolean existsByUserIdAndProductId(UUID userId, UUID productId);

    void deleteByUserIdAndProductId(UUID userId, UUID productId);

    Boolean existsByProductIdAndUserEmail(UUID productId, String email);

    void deleteByUserEmailAndProductId(String email, UUID productId);
}
