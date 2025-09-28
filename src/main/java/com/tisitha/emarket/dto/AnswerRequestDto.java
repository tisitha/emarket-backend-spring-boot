package com.tisitha.emarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDto {

    @NotNull(message = "Question Id is required")
    private Long questionId;

    @NotBlank
    @Size(max = 500)
    private String answer;
}
