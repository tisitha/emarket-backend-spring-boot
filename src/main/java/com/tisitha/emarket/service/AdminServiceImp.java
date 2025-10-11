package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.exception.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import com.tisitha.emarket.util.ObjectConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ReviewRepository reviewRepository;
    private final ProvinceRepository provinceRepository;
    private final PasswordEncoder passwordEncoder;
    private final SiteConfigRepository siteConfigRepository;

    @Override
    public ProductPageSortDto getProducts(AdminPanelGetDto adminPanelGetDto) {
        Sort sort = adminPanelGetDto.getDir().equalsIgnoreCase("asc")?Sort.by(adminPanelGetDto.getSortBy()).ascending():Sort.by(adminPanelGetDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(adminPanelGetDto.getPageNumber(),adminPanelGetDto.getPageSize(),sort);
        Page<Product> products =productRepository.findAll(pageable);
        return new ProductPageSortDto(
                products.getContent().stream().map(ObjectConverter::mapProductToProductDto).toList(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast()
        );
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public AccountPageSortDto getAccounts(AdminPanelGetDto adminPanelGetDto) {
        Sort sort = adminPanelGetDto.getDir().equalsIgnoreCase("asc")?Sort.by(adminPanelGetDto.getSortBy()).ascending():Sort.by(adminPanelGetDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(adminPanelGetDto.getPageNumber(),adminPanelGetDto.getPageSize(),sort);
        Page<User> users = userRepository.findAll(pageable);
        return new AccountPageSortDto(
                users.getContent().stream().map(ObjectConverter::mapAccountToAccountDto).toList(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isLast()
        );
    }

    @Override
    public AccountResponseDto getAccount(UUID accountId) {
        User user = userRepository.findById(accountId).orElseThrow(UserNotFoundException::new);
        return ObjectConverter.mapAccountToAccountDto(user);
    }

    @Override
    public AccountResponseDto addAdminAccount(UserRegisterDto userRegisterDto) {
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getPasswordRepeat())){
            throw new PasswordNotMatchException();
        }
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw new EmailTakenException();
        }
        Province province = provinceRepository.findById(userRegisterDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
        User user = new User();
        user.setFname(userRegisterDto.getFname());
        user.setLname(userRegisterDto.getLname());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setPhoneNo(userRegisterDto.getPhoneNo());
        user.setAddress(userRegisterDto.getAddress());
        user.setRole(Role.ROLE_USER);
        user.setProvince(province);
        User newUser = userRepository.save(user);
        return ObjectConverter.mapAccountToAccountDto(newUser);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        if(!userRepository.existsById(accountId)){
            throw new UserNotFoundException();
        }
        userRepository.deleteById(accountId);
    }

    @Override
    public ReviewPageSortDto getReviews(AdminPanelGetDto adminPanelGetDto) {
        Sort sort = adminPanelGetDto.getDir().equalsIgnoreCase("asc")?Sort.by(adminPanelGetDto.getSortBy()).ascending():Sort.by(adminPanelGetDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(adminPanelGetDto.getPageNumber(),adminPanelGetDto.getPageSize(),sort);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return new ReviewPageSortDto(
                reviews.getContent().stream().map(ObjectConverter::mapReviewToReviewDto).toList(),
                reviews.getNumberOfElements(),
                reviews.getTotalPages(),
                reviews.isLast());
    }

    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        Review review =reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        return ObjectConverter.mapReviewToReviewDto(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public QuestionPageSortDto getQuestions(AdminPanelGetDto adminPanelGetDto) {
        Sort sort = adminPanelGetDto.getDir().equalsIgnoreCase("asc")?Sort.by(adminPanelGetDto.getSortBy()).ascending():Sort.by(adminPanelGetDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(adminPanelGetDto.getPageNumber(),adminPanelGetDto.getPageSize(),sort);
        Page<Question> questions = questionRepository.findAll(pageable);
        return new QuestionPageSortDto(
                questions.getContent().stream().map(ObjectConverter::mapQuestionToQuestionDto).toList(),
                questions.getTotalElements(),
                questions.getTotalPages(),
                questions.isLast()
        );
    }

    @Override
    public QuestionResponseDto getQuestion(Long questionId) {
        Question question =questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        return ObjectConverter.mapQuestionToQuestionDto(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        questionRepository.deleteById(questionId);
    }

    @Override
    public Double getDeliveryCost() {
        SiteConfig deliveryCostConfig = siteConfigRepository.findByName(SiteConfigName.DELIVERY_COST.name()).get();
        return Double.parseDouble(deliveryCostConfig.getValue());
    }

    @Override
    public Double changeDeliveryCost(Double cost) {
        SiteConfig deliveryCostConfig = siteConfigRepository.findByName(SiteConfigName.DELIVERY_COST.name()).get();
        deliveryCostConfig.setValue(String.valueOf(cost));
        SiteConfig newDeliveryCostConfig = siteConfigRepository.save(deliveryCostConfig);
        return Double.parseDouble(newDeliveryCostConfig.getValue());
    }
}