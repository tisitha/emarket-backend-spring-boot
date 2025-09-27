package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ReviewGetRequestDto;
import com.tisitha.emarket.dto.ReviewPageSortDto;
import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;

public interface ReviewService {

    ReviewPageSortDto getReviewTitles(ReviewGetRequestDto reviewGetRequestDto);

    ReviewResponseDto getReviewTitle(Long reviewId);

    ReviewResponseDto addReviewTitle(ReviewRequestDto reviewRequestDto);

    ReviewResponseDto updateReviewTitle(Long reviewId,ReviewRequestDto reviewRequestDto);

    void deleteReviewTitle(Long reviewId);

}
