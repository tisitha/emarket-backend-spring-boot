package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ProvinceRequestDto;
import com.tisitha.emarket.dto.ProvinceResponseDto;
import com.tisitha.emarket.exception.ProvinceNotFoundException;
import com.tisitha.emarket.model.Province;
import com.tisitha.emarket.repo.ProvinceRepository;
import com.tisitha.emarket.util.ObjectConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImp implements ProvinceService{

    private final ProvinceRepository provinceRepository;

    public ProvinceServiceImp(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<ProvinceResponseDto> getProvinceTitles() {
        List<Province> warranties = provinceRepository.findAll();
        return warranties.stream().map(ObjectConverter::mapProvinceToProvinceDto).toList();
    }

    @Override
    public ProvinceResponseDto getProvinceTitle(Long provinceId) {
        Province province = provinceRepository.findById(provinceId).orElseThrow(ProvinceNotFoundException::new);
        return ObjectConverter.mapProvinceToProvinceDto(province);
    }

    @Override
    public ProvinceResponseDto addProvinceTitle(ProvinceRequestDto provinceRequestDto) {
        Province province = new Province();
        province.setName(provinceRequestDto.getName());
        Province newProvince = provinceRepository.save(province);
        return ObjectConverter.mapProvinceToProvinceDto(newProvince);
    }

    @Override
    public ProvinceResponseDto updateProvinceTitle(Long provinceId,ProvinceRequestDto provinceRequestDto) {
        Province oldProvince = provinceRepository.findById(provinceId).orElseThrow(ProvinceNotFoundException::new);
        oldProvince.setName(provinceRequestDto.getName());
        Province newProvince = provinceRepository.save(oldProvince);
        return ObjectConverter.mapProvinceToProvinceDto(newProvince);
    }

    @Override
    public void deleteProvinceTitle(Long provinceId) {
        provinceRepository.findById(provinceId).orElseThrow(ProvinceNotFoundException::new);
        provinceRepository.deleteById(provinceId);
    }

}
