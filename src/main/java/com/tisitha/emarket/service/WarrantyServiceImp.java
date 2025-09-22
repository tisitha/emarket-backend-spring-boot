package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.WarrantyRequestDto;
import com.tisitha.emarket.dto.WarrantyResponseDto;
import com.tisitha.emarket.model.Warranty;
import com.tisitha.emarket.repo.WarrantyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarrantyServiceImp implements WarrantyService{

    private final WarrantyRepository warrantyRepository;

    public WarrantyServiceImp(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    @Override
    public List<WarrantyResponseDto> getWarrantyTitles() {
        List<Warranty> warranties = warrantyRepository.findAll();
        return warranties.stream().map(this::mapWarrantyToWarrantyDto).toList();
    }

    @Override
    public WarrantyResponseDto getWarrantyTitle(Long warrantyId) {
        Warranty warranty = warrantyRepository.findById(warrantyId).orElseThrow(()->new RuntimeException(""));
        return mapWarrantyToWarrantyDto(warranty);
    }

    @Override
    public WarrantyResponseDto addWarrantyTitle(WarrantyRequestDto warrantyRequestDto) {
        Warranty warranty = new Warranty();
        warranty.setName(warrantyRequestDto.getName());
        Warranty newWarranty = warrantyRepository.save(warranty);
        return mapWarrantyToWarrantyDto(newWarranty);
    }

    @Override
    public WarrantyResponseDto updateWarrantyTitle(Long warrantyId,WarrantyRequestDto warrantyRequestDto) {
        Warranty oldWarranty = warrantyRepository.findById(warrantyId).orElseThrow(()->new RuntimeException(""));
        oldWarranty.setName(warrantyRequestDto.getName());
        Warranty newWarranty = warrantyRepository.save(oldWarranty);
        return mapWarrantyToWarrantyDto(newWarranty);
    }

    @Override
    public void deleteWarrantyTitle(Long warrantyId) {
        warrantyRepository.findById(warrantyId).orElseThrow(()->new RuntimeException(""));
        warrantyRepository.deleteById(warrantyId);
    }

    private WarrantyResponseDto mapWarrantyToWarrantyDto(Warranty warranty){
        return new WarrantyResponseDto(warranty.getId(),warranty.getName(),warranty.getProducts());
    }

}
