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
public class CarouselRequestDto {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull(message = "Hidden field is required")
    private Boolean hidden;

}
