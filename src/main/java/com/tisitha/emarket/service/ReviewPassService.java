package com.tisitha.emarket.service;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface ReviewPassService {

    Boolean checkReviewPass(UUID productId, Authentication authentication);

}
