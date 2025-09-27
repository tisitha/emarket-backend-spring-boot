package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ReviewGetRequestDto;
import com.tisitha.emarket.dto.ReviewPageSortDto;
import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewPassRepository reviewPassRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public ReviewPageSortDto getReviewTitles(ReviewGetRequestDto reviewGetRequestDto) {
        Sort sort = reviewGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(reviewGetRequestDto.getSortBy()).ascending():Sort.by(reviewGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(reviewGetRequestDto.getPageNumber(),reviewGetRequestDto.getPageSize(),sort);
        Page<Review> reviews =reviewRepository.findAllByProductId(reviewGetRequestDto.getProductId(),pageable);
        return new ReviewPageSortDto(
                reviews.getContent().stream().map(this::mapReviewToReviewDto).toList(),
                reviews.getNumberOfElements(),
                reviews.getTotalPages(),
                reviews.isLast());
    }

    @Override
    public ReviewResponseDto getReviewTitle(Long reviewId) {
        Review review =reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException(""));
        return mapReviewToReviewDto(review);
    }

    @Override
    @Transactional
    public ReviewResponseDto addReviewTitle(ReviewRequestDto reviewRequestDto, Authentication authentication) {
        if(!reviewPassRepository.existsByProductIdUserEmail(reviewRequestDto.getProductId(),authentication.getName())){
            throw new RuntimeException("");
        }
        reviewPassRepository.deleteByUserEmailAndProductId(authentication.getName(),reviewRequestDto.getProductId());
        Product product = productRepository.findById(reviewRequestDto.getProductId()).orElseThrow(()->new RuntimeException(""));
        User user = (User) authentication.getPrincipal();
        Review review = new Review();
        review.setBody(reviewRequestDto.getBody());
        review.setRate(reviewRequestDto.getRate());
        review.setDate(new Date());
        review.setProduct(product);
        review.setUser(user);
        review.setEdited(false);
        Review newReview = reviewRepository.save(review);
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(product.getVendorProfile().getUser());
        notification.setNotificationType(NotificationType.PRODUCT);
        notification.setAttachedId(product.getId().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage(product.getName()+"\n"+user.getFname()+" rated "+review.getRate()+".");
        notificationRepository.save(notification);
        return mapReviewToReviewDto(newReview);
    }

    @Override
    public ReviewResponseDto updateReviewTitle(Long reviewId, ReviewRequestDto reviewRequestDto, Authentication authentication) {
        Review review = reviewRepository.findByIdAndUserEmail(reviewId,authentication.getName()).orElseThrow(()->new RuntimeException(""));
        review.setBody(reviewRequestDto.getBody());
        review.setRate(reviewRequestDto.getRate());
        review.setEdited(true);
        Review newReview = reviewRepository.save(review);
        return mapReviewToReviewDto(newReview);
    }

    @Override
    public void deleteReviewTitle(Long reviewId, Authentication authentication) {
        reviewRepository.findByIdAndUserEmail(reviewId,authentication.getName()).orElseThrow(()->new RuntimeException(""));
        reviewRepository.deleteById(reviewId);
    }

    private ReviewResponseDto mapReviewToReviewDto(Review review){
        return new ReviewResponseDto(
                review.getId(),
                review.getBody(),
                review.getRate(),
                review.getDate(),
                review.getProduct(),
                review.getUser(),
                review.isEdited()
        );
    }
}
