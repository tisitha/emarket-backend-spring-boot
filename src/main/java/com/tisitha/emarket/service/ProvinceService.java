package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProvinceRequestDto;
import com.tisitha.emarket.dto.ProvinceResponseDto;

import java.util.List;

public interface ProvinceService {

    List<ProvinceResponseDto> getProvinceTitles();

    ProvinceResponseDto getProvinceTitle(Long provinceId);

    ProvinceResponseDto addProvinceTitle(ProvinceRequestDto provinceRequestDto);

    ProvinceResponseDto updateProvinceTitle(Long provinceId,ProvinceRequestDto provinceRequestDto);

    void deleteProvinceTitle(Long provinceId);

}
