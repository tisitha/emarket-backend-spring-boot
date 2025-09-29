package com.tisitha.emarket.cotroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisitha.emarket.dto.CarouselRequestDto;
import com.tisitha.emarket.dto.CarouselResponseDto;
import com.tisitha.emarket.exception.InvalidJsonFormatException;
import com.tisitha.emarket.service.CarouselService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarouselController {

    private final CarouselService carouselService;
    private final ObjectMapper objectMapper;

    public CarouselController(CarouselService carouselService, ObjectMapper objectMapper) {
        this.carouselService = carouselService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/open/carousel")
    ResponseEntity<List<CarouselResponseDto>> getCarousels(){
        return new ResponseEntity<>(carouselService.getCarousels(), HttpStatus.OK);
    }

    @PostMapping("/admin/carousel")
    ResponseEntity<Void> addCarousel(@RequestPart String carouselRequestDto,@NotEmpty @RequestPart MultipartFile file){
        carouselService.addCarousel(stringToCarouselRequestDto(carouselRequestDto),file);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @PutMapping("/admin/carousel/{carouselId}")
    ResponseEntity<Void> updateCarousel(@PathVariable Long carouselId,@RequestBody CarouselRequestDto carouselRequestDto){
        carouselService.updateCarousel(carouselId,carouselRequestDto);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/carousel/{carouselId}")
    ResponseEntity<Void> deleteCarousel(@PathVariable Long carouselId){
        carouselService.deleteCarousel(carouselId);
        return ResponseEntity.ok().build();
    }

    private CarouselRequestDto stringToCarouselRequestDto(String dataString){
        try {
            return objectMapper.readValue(dataString,CarouselRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonFormatException("InputDto is invalid json format");
        }
    }
}
