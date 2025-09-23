package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.ProvinceRequestDto;
import com.tisitha.emarket.dto.ProvinceResponseDto;
import com.tisitha.emarket.service.ProvinceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseEntity<List<ProvinceResponseDto>> getProvinces(){
        return new ResponseEntity<>(provinceService.getProvinceTitles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProvinceResponseDto> addProvince(@RequestBody ProvinceRequestDto provinceRequestDto){
        return new ResponseEntity<>(provinceService.addProvinceTitle(provinceRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceResponseDto> editProvince(@PathVariable Long provinceId, @RequestBody ProvinceRequestDto provinceRequestDto){
        return new ResponseEntity<>(provinceService.updateProvinceTitle(provinceId,provinceRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long provinceId){
        provinceService.deleteProvinceTitle(provinceId);
        return ResponseEntity.ok().build();
    }

}
