package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.ReviewGetRequestDto;
import com.tisitha.emarket.dto.ReviewPageSortDto;
import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;
import com.tisitha.emarket.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/open/review")
    public ResponseEntity<ReviewPageSortDto> getReviewTitles(@RequestBody ReviewGetRequestDto reviewGetRequestDto) {
        return new ResponseEntity<>(reviewService.getReviewTitles(reviewGetRequestDto), HttpStatus.OK);
    }

    @PostMapping("/review")
    public ResponseEntity<ReviewResponseDto> addReviewTitle(@RequestBody ReviewRequestDto reviewRequestDto, Authentication authentication) {
        return new ResponseEntity<>(reviewService.addReviewTitle(reviewRequestDto,authentication),HttpStatus.CREATED);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewTitle(@PathVariable Long reviewId,@RequestBody ReviewRequestDto reviewRequestDto, Authentication authentication) {
        return new ResponseEntity<>(reviewService.updateReviewTitle(reviewId,reviewRequestDto,authentication),HttpStatus.CREATED);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteReviewTitle(@PathVariable Long reviewId, Authentication authentication) {
        reviewService.deleteReviewTitle(reviewId,authentication);
        return ResponseEntity.ok().build();
    }

}
