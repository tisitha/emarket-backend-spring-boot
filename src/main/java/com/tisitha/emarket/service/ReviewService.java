package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ReviewGetRequestDto;
import com.tisitha.emarket.dto.ReviewPageSortDto;
import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;
import org.springframework.security.core.Authentication;

public interface ReviewService {

    ReviewPageSortDto getReviewTitles(ReviewGetRequestDto reviewGetRequestDto);

    ReviewResponseDto getReviewTitle(Long reviewId);

    ReviewResponseDto addReviewTitle(ReviewRequestDto reviewRequestDto, Authentication authentication);

    ReviewResponseDto updateReviewTitle(Long reviewId,ReviewRequestDto reviewRequestDto, Authentication authentication);

    void deleteReviewTitle(Long reviewId, Authentication authentication);

}
