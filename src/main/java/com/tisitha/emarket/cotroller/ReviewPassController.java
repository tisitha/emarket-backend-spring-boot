package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.CheckReviewPassDto;
import com.tisitha.emarket.service.ReviewPassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reveiwpass")
public class ReviewPassController {

    private final ReviewPassService reviewPassService;

    public ReviewPassController(ReviewPassService reviewPassService) {
        this.reviewPassService = reviewPassService;
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkReviewPass(CheckReviewPassDto checkReviewPassDto){
        return new ResponseEntity<>(reviewPassService.checkReviewPass(checkReviewPassDto), HttpStatus.OK);
    }


}
