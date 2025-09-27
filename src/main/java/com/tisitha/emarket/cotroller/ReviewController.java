package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.ReviewGetRequestDto;
import com.tisitha.emarket.dto.ReviewPageSortDto;
import com.tisitha.emarket.dto.ReviewRequestDto;
import com.tisitha.emarket.dto.ReviewResponseDto;
import com.tisitha.emarket.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<ReviewPageSortDto> getReviewTitles(@RequestBody ReviewGetRequestDto reviewGetRequestDto) {
        return new ResponseEntity<>(reviewService.getReviewTitles(reviewGetRequestDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> addReviewTitle(@RequestBody ReviewRequestDto reviewRequestDto) {
        return new ResponseEntity<>(reviewService.addReviewTitle(reviewRequestDto),HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewTitle(@PathVariable Long reviewId,@RequestBody ReviewRequestDto reviewRequestDto) {
        return new ResponseEntity<>(reviewService.updateReviewTitle(reviewId,reviewRequestDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewTitle(@PathVariable Long reviewId) {
        reviewService.deleteReviewTitle(reviewId);
        return ResponseEntity.ok().build();
    }

}
