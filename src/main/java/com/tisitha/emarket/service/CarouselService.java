package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CarouselRequestDto;
import com.tisitha.emarket.dto.CarouselResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarouselService {

    List<CarouselResponseDto> getCarousels();

    void addCarousel(CarouselRequestDto carouselRequestDto, MultipartFile file);

    void updateCarousel(Long carouselId,CarouselRequestDto carouselRequestDto);

    void deleteCarousel(Long carouselId);

}
