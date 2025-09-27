package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.WarrantyRequestDto;
import com.tisitha.emarket.dto.WarrantyResponseDto;
import com.tisitha.emarket.service.WarrantyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class WarrantyController {

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @GetMapping("/open/warranty")
    public ResponseEntity<List<WarrantyResponseDto>> getWarrantyTypes(){
        return new ResponseEntity<>(warrantyService.getWarrantyTitles(), HttpStatus.OK);
    }

    @PostMapping("/admin/warranty")
    public ResponseEntity<WarrantyResponseDto> addWarrantyType(@RequestBody WarrantyRequestDto warrantyRequestDto){
        return new ResponseEntity<>(warrantyService.addWarrantyTitle(warrantyRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/warranty/{id}")
    public ResponseEntity<WarrantyResponseDto> editWarrantyType(@PathVariable Long warrantyId,@RequestBody WarrantyRequestDto warrantyRequestDto){
        return new ResponseEntity<>(warrantyService.updateWarrantyTitle(warrantyId,warrantyRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/warranty/{id}")
    public ResponseEntity<Void> deleteWarrantyType(@PathVariable Long warrantyId){
        warrantyService.deleteWarrantyTitle(warrantyId);
        return ResponseEntity.ok().build();
    }

}
