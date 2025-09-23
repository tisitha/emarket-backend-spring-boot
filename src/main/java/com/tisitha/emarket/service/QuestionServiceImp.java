package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;
import com.tisitha.emarket.model.Question;
import com.tisitha.emarket.repo.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService{

    private final QuestionRepository questionRepository;

    public QuestionServiceImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionResponseDto> getQuestionTitles() {
        List<Question> questions =questionRepository.findAll();
        return questions.stream().map(this::mapQuestionToQuestionDto).toList();
    }

    @Override
    public QuestionResponseDto getQuestionTitle(Long questionId) {
        Question question =questionRepository.findById(questionId).orElseThrow(()->new RuntimeException(""));
        return mapQuestionToQuestionDto(question);
    }

    @Override
    public QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto) {
        Question question = new Question();
        question.setQuestion(questionRequestDto.getQuestion());
        question.setAnswer(questionRequestDto.getAnswer());
        question.setProduct(questionRequestDto.getProduct());
        question.setUser(questionRequestDto.getUser());
        question.setDate(new Date());
        Question newQuestion = questionRepository.save(question);
        return mapQuestionToQuestionDto(newQuestion);
    }

    @Override
    public QuestionResponseDto updateQuestionTitle(Long questionId, QuestionRequestDto questionRequestDto) {
        Question question =questionRepository.findById(questionId).orElseThrow(()->new RuntimeException(""));
        question.setAnswer(questionRequestDto.getAnswer());
        Question newQuestion = questionRepository.save(question);
        return mapQuestionToQuestionDto(newQuestion);
    }

    @Override
    public void deleteQuestionTitle(Long questionId) {
        questionRepository.findById(questionId).orElseThrow(()->new RuntimeException(""));
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
