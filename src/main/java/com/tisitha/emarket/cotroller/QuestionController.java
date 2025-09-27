package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.QuestionGetRequestDto;
import com.tisitha.emarket.dto.QuestionPageSortDto;
import com.tisitha.emarket.dto.QuestionRequestDto;
import com.tisitha.emarket.dto.QuestionResponseDto;
import com.tisitha.emarket.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<QuestionPageSortDto> getAnsweredQuestions(@RequestBody QuestionGetRequestDto questionGetRequestDto){
        return new ResponseEntity<>(questionService.getAnsweredQuestionTitles(questionGetRequestDto), HttpStatus.OK);
    }

    @GetMapping("/vendor")
    public ResponseEntity<QuestionPageSortDto> getUnansweredQuestions(@RequestBody QuestionGetRequestDto questionGetRequestDto){
        return new ResponseEntity<>(questionService.getUnansweredQuestionTitles(questionGetRequestDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> addQuestion(@RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.addQuestionTitle(questionRequestDto),HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Long questionId,@RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.updateQuestionTitle(questionId,questionRequestDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestionTitle(questionId);
        return ResponseEntity.ok().build();
    }

}
