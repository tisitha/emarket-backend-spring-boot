package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {

    private String question;

    private String answer;

    private UUID productId;

    private UUID userId;

}
