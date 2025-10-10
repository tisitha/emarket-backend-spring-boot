package com.tisitha.emarket.service;

import com.tisitha.emarket.repo.ReviewPassRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewPassServiceImp implements  ReviewPassService{

    private final ReviewPassRepository reviewPassRepository;

    public ReviewPassServiceImp(ReviewPassRepository reviewPassRepository) {
        this.reviewPassRepository = reviewPassRepository;
    }

    @Override
    public Boolean checkReviewPass(UUID productId, Authentication authentication) {
        return reviewPassRepository.existsByProductIdAndUserEmail(productId,authentication.getName());
    }
}
