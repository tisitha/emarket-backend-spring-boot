package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/open/question")
    public ResponseEntity<QuestionPageSortDto> getAnsweredQuestions(@Valid @RequestBody QuestionGetRequestDto questionGetRequestDto){
        return new ResponseEntity<>(questionService.getAnsweredQuestionTitles(questionGetRequestDto), HttpStatus.OK);
    }

    @GetMapping("/question/vendor")
    public ResponseEntity<QuestionPageSortDto> getUnansweredQuestions(@Valid @RequestBody QuestionGetRequestDto questionGetRequestDto, Authentication authentication){
        return new ResponseEntity<>(questionService.getUnansweredQuestionTitles(questionGetRequestDto,authentication), HttpStatus.OK);
    }

    @PostMapping("/question")
    public ResponseEntity<QuestionResponseDto> addQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto,Authentication authentication) {
        return new ResponseEntity<>(questionService.addQuestionTitle(questionRequestDto,authentication),HttpStatus.CREATED);
    }

    @PutMapping("/question/answer")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@Valid @RequestBody AnswerRequestDto answerRequestDto, Authentication authentication) {
        return new ResponseEntity<>(questionService.updateQuestionTitle(answerRequestDto,authentication),HttpStatus.CREATED);
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId,Authentication authentication) {
        questionService.deleteQuestionTitle(questionId,authentication);
        return ResponseEntity.ok().build();
    }

}
