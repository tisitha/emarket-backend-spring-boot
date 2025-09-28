package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/product")
    ResponseEntity<ProductPageSortDto> getProducts(@RequestBody AdminPanelGetDto adminPanelGetDto){
        return new ResponseEntity<>(adminService.getProducts(adminPanelGetDto), HttpStatus.OK);
    }

    @DeleteMapping("/product/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID productId){
        adminService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account")
    ResponseEntity<AccountPageSortDto> getAccounts(@RequestBody AdminPanelGetDto adminPanelGetDto){
        return new ResponseEntity<>(adminService.getAccounts(adminPanelGetDto),HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    ResponseEntity<AccountResponseDto> getAccount(@PathVariable UUID accountId){
        return new ResponseEntity<>(adminService.getAccount(accountId),HttpStatus.OK);
    }

    @PostMapping("/account")
    ResponseEntity<AccountResponseDto> addAdminAccount(@RequestBody UserRegisterDto userRegisterDto){
        return new ResponseEntity<>(adminService.addAdminAccount(userRegisterDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/account/{accountId}")
    ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId){
        adminService.deleteAccount(accountId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/review")
    ResponseEntity<ReviewPageSortDto> getReviews(@RequestBody AdminPanelGetDto adminPanelGetDto){
        return new ResponseEntity<>(adminService.getReviews(adminPanelGetDto),HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long reviewId){
        return new ResponseEntity<>(adminService.getReview(reviewId),HttpStatus.OK);
    }

    @DeleteMapping("/review/{reviewId}")
    ResponseEntity<Void> deleteReview(@PathVariable Long reviewId){
        adminService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/question")
    ResponseEntity<QuestionPageSortDto> getQuestions(@RequestBody AdminPanelGetDto adminPanelGetDto){
        return new ResponseEntity<>(adminService.getQuestions(adminPanelGetDto),HttpStatus.OK);
    }

    @GetMapping("/question/{questionId}")
    ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable Long questionId){
        return new ResponseEntity<>(adminService.getQuestion(questionId),HttpStatus.OK);
    }

    @DeleteMapping("/question/{questionId}")
    ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId){
        adminService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

}
