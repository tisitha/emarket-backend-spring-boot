package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.QuestionGetRequestDto;
import com.tisitha.emarket.dto.QuestionPageSortDto;
import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;
import org.springframework.security.core.Authentication;

public interface QuestionService {

    QuestionPageSortDto getAnsweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto);

    QuestionPageSortDto getUnansweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto,Authentication authentication);

    QuestionResponseDto getQuestionTitle(Long questionId);

    QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto,Authentication authentication);

    QuestionResponseDto updateQuestionTitle(Long questionId,QuestionRequestDto questionRequestDto,Authentication authentication);

    void deleteQuestionTitle(Long questionId, Authentication authentication);

}
