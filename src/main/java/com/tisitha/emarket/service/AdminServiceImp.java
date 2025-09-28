package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.exception.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final QuestionRepository questionRepository;
    private final ReviewRepository reviewRepository;
    private final ProvinceRepository provinceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProductPageSortDto getProducts(AdminPanelGetDto adminPanelGetDto) {
        Sort sort = adminPanelGetDto.getDir().equalsIgnoreCase("asc")?Sort.by(adminPanelGetDto.getSortBy()).ascending():Sort.by(adminPanelGetDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(adminPanelGetDto.getPageNumber(),adminPanelGetDto.getPageSize(),sort);
        Page<Product> products =productRepository.findAll(pageable);
        return new ProductPageSortDto(
                products.getContent().stream().map(this::mapProductToProductDto).toList(),
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
                users.getContent().stream().map(this::mapAccountToAccountDto).toList(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isLast()
        );
    }

    @Override
    public AccountResponseDto getAccount(UUID accountId) {
        User user = userRepository.findById(accountId).orElseThrow(UserNotFoundException::new);
        return mapAccountToAccountDto(user);
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
        return mapAccountToAccountDto(newUser);
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
                reviews.getContent().stream().map(this::mapReviewToReviewDto).toList(),
                reviews.getNumberOfElements(),
                reviews.getTotalPages(),
                reviews.isLast());
    }

    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        Review review =reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        return mapReviewToReviewDto(review);
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
                questions.getContent().stream().map(this::mapQuestionToQuestionDto).toList(),
                questions.getTotalElements(),
                questions.getTotalPages(),
                questions.isLast()
        );
    }

    @Override
    public QuestionResponseDto getQuestion(Long questionId) {
        Question question =questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        return mapQuestionToQuestionDto(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        questionRepository.deleteById(questionId);
    }

    private ProductResponseDto mapProductToProductDto(Product product){
        OptionalDouble average = product.getReviews().stream()
                .mapToInt(Review::getRate)
                .average();
        return new ProductResponseDto(
                product.getId(),
                product.getVendorProfile(),
                product.getName(),
                product.getImgUrl(),
                product.getDescription(),
                product.getPrice(),
                product.getDeal(),
                product.isCod(),
                product.isFreeDelivery(),
                product.getBrand(),
                product.getCategory(),
                product.getReviews(),
                average.isPresent()?average.getAsDouble():null,
                product.getProvince(),
                product.getWarranty(),
                product.getQuestions(),
                product.getCartItems(),
                product.getQuantity()
        );
    }

    private AccountResponseDto mapAccountToAccountDto(User user){
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(user.getId());
        accountResponseDto.setFname(user.getFname());
        accountResponseDto.setLname(user.getLname());
        accountResponseDto.setEmail(user.getEmail());
        accountResponseDto.setPhoneNo(user.getPhoneNo());
        accountResponseDto.setAddress(user.getAddress());
        accountResponseDto.setRole(user.getRole());
        accountResponseDto.setProvince(user.getProvince());
        Optional<VendorProfile> vendorProfile = vendorProfileRepository.findById(user.getId());
        if(vendorProfile.isEmpty()){
            return accountResponseDto;
        }
        accountResponseDto.setBusinessName(vendorProfile.get().getBusinessName());
        accountResponseDto.setBankAccountNo(vendorProfile.get().getBankAccountNo());
        accountResponseDto.setBank(vendorProfile.get().getBank());
        return accountResponseDto;
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

    private QuestionResponseDto mapQuestionToQuestionDto(Question question){
        return new QuestionResponseDto(
                question.getId(),
                question.getQuestion(),
                question.getAnswer(),
                question.getProduct(),
                question.getUser(),
                question.getDate()
        );
    }
}