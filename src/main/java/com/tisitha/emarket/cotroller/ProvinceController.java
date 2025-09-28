package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.ProvinceRequestDto;
import com.tisitha.emarket.dto.ProvinceResponseDto;
import com.tisitha.emarket.service.ProvinceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/open/province")
    public ResponseEntity<List<ProvinceResponseDto>> getProvinces(){
        return new ResponseEntity<>(provinceService.getProvinceTitles(), HttpStatus.OK);
    }

    @PostMapping("admin/province")
    public ResponseEntity<ProvinceResponseDto> addProvince(@Valid @RequestBody ProvinceRequestDto provinceRequestDto){
        return new ResponseEntity<>(provinceService.addProvinceTitle(provinceRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("admin/province/{id}")
    public ResponseEntity<ProvinceResponseDto> editProvince(@PathVariable Long provinceId,@Valid @RequestBody ProvinceRequestDto provinceRequestDto){
        return new ResponseEntity<>(provinceService.updateProvinceTitle(provinceId,provinceRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("admin/province/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long provinceId){
        provinceService.deleteProvinceTitle(provinceId);
        return ResponseEntity.ok().build();
    }

}
