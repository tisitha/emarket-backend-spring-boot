package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;

import java.util.List;

public interface QuestionService {

    List<QuestionResponseDto> getQuestionTitles();

    QuestionResponseDto getQuestionTitle(Long questionId);

    QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto);

    QuestionResponseDto updateQuestionTitle(Long questionId,QuestionRequestDto questionRequestDto);

    void deleteQuestionTitle(Long questionId);

}
