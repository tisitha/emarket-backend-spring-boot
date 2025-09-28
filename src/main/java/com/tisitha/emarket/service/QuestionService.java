package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import org.springframework.security.core.Authentication;

public interface QuestionService {

    QuestionPageSortDto getAnsweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto);

    QuestionPageSortDto getUnansweredQuestionTitles(QuestionGetRequestDto questionGetRequestDto,Authentication authentication);

    QuestionResponseDto getQuestionTitle(Long questionId);

    QuestionResponseDto addQuestionTitle(QuestionRequestDto questionRequestDto,Authentication authentication);

    QuestionResponseDto updateQuestionTitle(AnswerRequestDto answerRequestDto, Authentication authentication);

    void deleteQuestionTitle(Long questionId, Authentication authentication);

}
