package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.QuestionGetRequestDto;
import com.tisitha.emarket.dto.QuestionPageSortDto;
import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;
import com.tisitha.emarket.service.QuestionService;
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
    public ResponseEntity<QuestionPageSortDto> getAnsweredQuestions(@RequestBody QuestionGetRequestDto questionGetRequestDto){
        return new ResponseEntity<>(questionService.getAnsweredQuestionTitles(questionGetRequestDto), HttpStatus.OK);
    }

    @GetMapping("/question/vendor")
    public ResponseEntity<QuestionPageSortDto> getUnansweredQuestions(@RequestBody QuestionGetRequestDto questionGetRequestDto, Authentication authentication){
        return new ResponseEntity<>(questionService.getUnansweredQuestionTitles(questionGetRequestDto,authentication), HttpStatus.OK);
    }

    @PostMapping("/question")
    public ResponseEntity<QuestionResponseDto> addQuestion(@RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.addQuestionTitle(questionRequestDto),HttpStatus.CREATED);
    }

    @PutMapping("/question/{questionId}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Long questionId,@RequestBody QuestionRequestDto questionRequestDto,Authentication authentication) {
        return new ResponseEntity<>(questionService.updateQuestionTitle(questionId,questionRequestDto,authentication),HttpStatus.CREATED);
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId,Authentication authentication) {
        questionService.deleteQuestionTitle(questionId,authentication);
        return ResponseEntity.ok().build();
    }

}
