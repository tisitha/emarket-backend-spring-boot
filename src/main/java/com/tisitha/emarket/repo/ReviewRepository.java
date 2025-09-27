package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    Page<Review> findAllByProductId(UUID productID,Pageable pageable);
}
