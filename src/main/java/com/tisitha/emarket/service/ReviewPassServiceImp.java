package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CheckReviewPassDto;
import com.tisitha.emarket.repo.ReviewPassRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewPassServiceImp implements  ReviewPassService{

    private final ReviewPassRepository reviewPassRepository;

    public ReviewPassServiceImp(ReviewPassRepository reviewPassRepository) {
        this.reviewPassRepository = reviewPassRepository;
    }

    @Override
    public Boolean checkReviewPass(CheckReviewPassDto checkReviewPassDto) {
        return reviewPassRepository.existsByUserIdAndProductId(checkReviewPassDto.getUserId(),checkReviewPassDto.getProductId());
    }
}
