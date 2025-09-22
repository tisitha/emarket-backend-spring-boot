package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.WarrantyRequestDto;
import com.tisitha.emarket.dto.WarrantyResponseDto;

import java.util.List;

public interface WarrantyService {

    List<WarrantyResponseDto> getWarrantyTitles();

    WarrantyResponseDto getWarrantyTitle(Long warrantyId);

    WarrantyResponseDto addWarrantyTitle(WarrantyRequestDto warrantyRequestDto);

    WarrantyResponseDto updateWarrantyTitle(Long warrantyId,WarrantyRequestDto warrantyRequestDto);

    void deleteWarrantyTitle(Long warrantyId);

}
