package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseDto> getReviewTitles();

    ReviewResponseDto getReviewTitle(Long reviewId);

    ReviewResponseDto addReviewTitle(ReviewRequestDto reviewRequestDto);

    ReviewResponseDto updateReviewTitle(Long reviewId,ReviewRequestDto reviewRequestDto);

    void deleteReviewTitle(Long reviewId);

}
