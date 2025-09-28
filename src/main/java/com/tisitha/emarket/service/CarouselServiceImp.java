package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CarouselRequestDto;
import com.tisitha.emarket.dto.CarouselResponseDto;
import com.tisitha.emarket.exception.InvalidInputException;
import com.tisitha.emarket.model.Carousel;
import com.tisitha.emarket.repo.CarouselRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CarouselServiceImp implements CarouselService{

    private final CarouselRepository carouselRepository;
    private final SupabaseService supabaseService;

    public CarouselServiceImp(CarouselRepository carouselRepository, SupabaseService supabaseService) {
        this.carouselRepository = carouselRepository;
        this.supabaseService = supabaseService;
    }

    @Override
    public List<CarouselResponseDto> getCarousels() {
        List<Carousel> carousels = carouselRepository.findAll();
        return carousels.stream().map(this::mapCarouselToCarouselDto).toList();
    }

    @Override
    public void addCarousel(@Valid CarouselRequestDto carouselRequestDto, MultipartFile file) {
        Carousel carousel = new Carousel();
        String imgUrl = supabaseService.upload("carousel",file);
        carousel.setName(carouselRequestDto.getName());
        carousel.setHidden(carouselRequestDto.getHidden());
        carousel.setImgUrl(imgUrl);
        carouselRepository.save(carousel);
    }

    @Override
    public void updateCarousel(Long carouselId, CarouselRequestDto carouselRequestDto) {
        Carousel carousel = carouselRepository.findById(carouselId).orElseThrow(InvalidInputException::new);
        Optional.ofNullable(carouselRequestDto.getName()).ifPresent(carousel::setName);
        Optional.ofNullable(carouselRequestDto.getHidden()).ifPresent(carousel::setHidden);
        carouselRepository.save(carousel);
    }

    @Override
    public void deleteCarousel(Long carouselId) {
        carouselRepository.findById(carouselId).orElseThrow(InvalidInputException::new);
        carouselRepository.deleteById(carouselId);
    }

    private CarouselResponseDto mapCarouselToCarouselDto(Carousel carousel){
        return new CarouselResponseDto(carousel.getId(), carousel.getName(), carousel.getImgUrl(), carousel.isHidden());
    }
}
