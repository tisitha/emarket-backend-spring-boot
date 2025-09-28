package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.QuestionGetRequestDto;
import com.tisitha.emarket.dto.QuestionPageSortDto;
import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;
import com.tisitha.emarket.exception.ProductNotFoundException;
import com.tisitha.emarket.exception.QuestionNotFoundException;
import com.tisitha.emarket.exception.UserNotFoundException;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.NotificationRepository;
import com.tisitha.emarket.repo.ProductRepository;
import com.tisitha.emarket.repo.QuestionRepository;
import com.tisitha.emarket.repo.UserRepository;
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
public class QuestionServiceImp implements QuestionService{

    private final QuestionRepository questionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public QuestionPageSortDto getAnsweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto) {
        Sort sort = questionGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(questionGetRequestDto.getSortBy()).ascending():Sort.by(questionGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(questionGetRequestDto.getPageNumber(),questionGetRequestDto.getPageSize(),sort);
        Page<Question> questions =questionRepository.findAllByProductIdAndAnswerIsNotNull(questionGetRequestDto.getProductId(),pageable);
        return new QuestionPageSortDto(
                questions.getContent().stream().map(this::mapQuestionToQuestionDto).toList(),
                questions.getTotalElements(),
                questions.getTotalPages(),
                questions.isLast()
        );
    }

    @Override
    public QuestionPageSortDto getUnansweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto, Authentication authentication) {
        Sort sort = questionGetRequestDto.getDir().equalsIgnoreCase("asc")?Sort.by(questionGetRequestDto.getSortBy()).ascending():Sort.by(questionGetRequestDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(questionGetRequestDto.getPageNumber(),questionGetRequestDto.getPageSize(),sort);
        Page<Question> questions =questionRepository.findAllByProductVendorProfileUserEmailAndAnswerIsNull(authentication.getName(),pageable);
        return new QuestionPageSortDto(
                questions.getContent().stream().map(this::mapQuestionToQuestionDto).toList(),
                questions.getTotalElements(),
                questions.getTotalPages(),
                questions.isLast()
        );
    }

    @Override
    public QuestionResponseDto getQuestionTitle(Long questionId) {
        Question question =questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        return mapQuestionToQuestionDto(question);
    }

    @Override
    @Transactional
    public QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto,Authentication authentication) {
        Question question = new Question();
        question.setQuestion(questionRequestDto.getQuestion());
        question.setAnswer(questionRequestDto.getAnswer());
        Product product = productRepository.findById(questionRequestDto.getProductId()).orElseThrow(ProductNotFoundException::new);
        question.setProduct(product);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        question.setUser(user);
        question.setDate(new Date());
        Question newQuestion = questionRepository.save(question);
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(product.getVendorProfile().getUser());
        notification.setNotificationType(NotificationType.QUESTION);
        notification.setAttachedId(newQuestion.getId().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage(product.getName()+"\n"+user.getFname()+" asked a question.");
        notificationRepository.save(notification);
        return mapQuestionToQuestionDto(newQuestion);
    }

    @Override
    @Transactional
    public QuestionResponseDto updateQuestionTitle(Long questionId, QuestionRequestDto questionRequestDto,Authentication authentication) {
        Question question =questionRepository.findByIdAndProductVendorProfileUserEmail(questionId,authentication.getName()).orElseThrow(QuestionNotFoundException::new);
        question.setAnswer(questionRequestDto.getAnswer());
        Question newQuestion = questionRepository.save(question);
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(question.getProduct().getVendorProfile().getUser());
        notification.setNotificationType(NotificationType.PRODUCT);
        notification.setAttachedId(question.getProduct().toString());
        notification.setDateAndTime(LocalDateTime.now(ZoneId.of("+05:30")));
        notification.setMessage(question.getProduct().getName()+"\nThe vendor replied to your question.");
        notificationRepository.save(notification);
        return mapQuestionToQuestionDto(newQuestion);
    }

    @Override
    public void deleteQuestionTitle(Long questionId,Authentication authentication) {
        questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        questionRepository.deleteById(questionId);
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
