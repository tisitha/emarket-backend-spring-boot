package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.service.ReviewPassService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/reveiwpass")
public class ReviewPassController {

    private final ReviewPassService reviewPassService;

    public ReviewPassController(ReviewPassService reviewPassService) {
        this.reviewPassService = reviewPassService;
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> checkReviewPass(@NotNull @PathVariable UUID productId, Authentication authentication){
        return new ResponseEntity<>(reviewPassService.checkReviewPass(productId,authentication), HttpStatus.OK);
    }


}
