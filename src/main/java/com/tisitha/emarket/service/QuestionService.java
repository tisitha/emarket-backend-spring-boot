package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuestionService {

    QuestionPageSortDto getAnsweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto);

    List<QuestionResponseDto> getUnansweredQuestionTitles(Authentication authentication);

    QuestionResponseDto getQuestionTitle(Long questionId);

    QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto,Authentication authentication);

    QuestionResponseDto updateQuestionTitle(AnswerRequestDto answerRequestDto, Authentication authentication);

    void deleteQuestionTitle(Long questionId, Authentication authentication);

}
