package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;

import java.util.UUID;

public interface AdminService {

    ProductPageSortDto getProducts(AdminPanelGetDto adminPanelGetDto);

    void deleteProduct(UUID productId);

    AccountPageSortDto getAccounts(AdminPanelGetDto adminPanelGetDto);

    AccountResponseDto getAccount(UUID accountId);

    AccountResponseDto addAdminAccount(UserRegisterDto userRegisterDto);

    void deleteAccount(UUID accountId);

    ReviewPageSortDto getReviews(AdminPanelGetDto adminPanelGetDto);

    ReviewResponseDto getReview(Long reviewId);

    void deleteReview(Long reviewId);

    QuestionPageSortDto getQuestions(AdminPanelGetDto adminPanelGetDto);

    QuestionResponseDto getQuestion(Long questionId);

    void deleteQuestion(Long questionId);
}
