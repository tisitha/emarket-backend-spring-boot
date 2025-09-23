package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.WarrantyRequestDto;
import com.tisitha.emarket.dto.WarrantyResponseDto;
import com.tisitha.emarket.service.WarrantyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/warranty")
@RestController
public class WarrantyController {

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @GetMapping
    public ResponseEntity<List<WarrantyResponseDto>> getWarrantyTypes(){
        return new ResponseEntity<>(warrantyService.getWarrantyTitles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WarrantyResponseDto> addWarrantyType(@RequestBody WarrantyRequestDto warrantyRequestDto){
        return new ResponseEntity<>(warrantyService.addWarrantyTitle(warrantyRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarrantyResponseDto> editWarrantyType(@PathVariable Long warrantyId,@RequestBody WarrantyRequestDto warrantyRequestDto){
        return new ResponseEntity<>(warrantyService.updateWarrantyTitle(warrantyId,warrantyRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarrantyType(@PathVariable Long warrantyId){
        warrantyService.deleteWarrantyTitle(warrantyId);
        return ResponseEntity.ok().build();
    }

}
