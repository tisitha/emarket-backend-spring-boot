package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;
import com.tisitha.emarket.model.Review;
import com.tisitha.emarket.repo.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewServiceImp(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewResponseDto> getReviewTitles() {
        List<Review> reviews =reviewRepository.findAll();
        return reviews.stream().map(this::mapReviewToReviewDto).toList();
    }

    @Override
    public ReviewResponseDto getReviewTitle(Long reviewId) {
        Review review =reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException(""));
        return mapReviewToReviewDto(review);
    }

    @Override
    public ReviewResponseDto addReviewTitle(ReviewRequestDto reviewRequestDto) {
        Review review = new Review();
        review.setUid(reviewRequestDto.getUid());
        review.setPid(reviewRequestDto.getPid());
        review.setBody(reviewRequestDto.getBody());
        review.setRate(reviewRequestDto.getRate());
        review.setDate(new Date());
        review.setProduct(reviewRequestDto.getProduct());
        review.setUser(reviewRequestDto.getUser());
        review.setEdited(false);
        Review newReview = reviewRepository.save(review);
        return mapReviewToReviewDto(newReview);
    }

    @Override
    public ReviewResponseDto updateReviewTitle(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException(""));
        review.setBody(reviewRequestDto.getBody());
        review.setRate(reviewRequestDto.getRate());
        review.setEdited(true);
        Review newReview = reviewRepository.save(review);
        return mapReviewToReviewDto(newReview);
    }

    @Override
    public void deleteReviewTitle(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException(""));
        reviewRepository.deleteById(reviewId);
    }

    private ReviewResponseDto mapReviewToReviewDto(Review review){
        return new ReviewResponseDto(
                review.getId(),
                review.getUid(),
                review.getPid(),
                review.getBody(),
                review.getRate(),
                review.getDate(),
                review.getProduct(),
                review.getUser(),
                review.isEdited()
        );
    }
}
