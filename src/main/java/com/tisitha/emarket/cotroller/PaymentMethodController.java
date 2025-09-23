package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.PaymentMethodRequestDto;
import com.tisitha.emarket.dto.WarrantyRequestDto;
import com.tisitha.emarket.dto.PaymentMethodResponseDto;
import com.tisitha.emarket.service.PaymentMethodService;
import com.tisitha.emarket.service.WarrantyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentmethod")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodResponseDto>> getPaymentMethods(){
        return new ResponseEntity<>(paymentMethodService.getPaymentMethodTitles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentMethodResponseDto> addPaymentMethod(@RequestBody PaymentMethodRequestDto paymentMethodRequestDto){
        return new ResponseEntity<>(paymentMethodService.addPaymentMethodTitle(paymentMethodRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodResponseDto> editPaymentMethod(@PathVariable Long pmId,@RequestBody PaymentMethodRequestDto paymentMethodRequestDto){
        return new ResponseEntity<>(paymentMethodService.updatePaymentMethodTitle(pmId,paymentMethodRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long pmId){
        paymentMethodService.deletePaymentMethodTitle(pmId);
        return ResponseEntity.ok().build();
    }

}