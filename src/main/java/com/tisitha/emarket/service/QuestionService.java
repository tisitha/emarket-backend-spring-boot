package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.QuestionGetRequestDto;
import com.tisitha.emarket.dto.QuestionPageSortDto;
import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;

public interface QuestionService {

    QuestionPageSortDto getAnsweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto);

    QuestionPageSortDto getUnansweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto);

    QuestionResponseDto getQuestionTitle(Long questionId);

    QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto);

    QuestionResponseDto updateQuestionTitle(Long questionId,QuestionRequestDto questionRequestDto);

    void deleteQuestionTitle(Long questionId);

}
