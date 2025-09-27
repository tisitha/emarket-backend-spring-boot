package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.PaymentMethodRequestDto;
import com.tisitha.emarket.dto.PaymentMethodResponseDto;
import com.tisitha.emarket.service.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/open/paymentmethod")
    public ResponseEntity<List<PaymentMethodResponseDto>> getPaymentMethods(){
        return new ResponseEntity<>(paymentMethodService.getPaymentMethodTitles(), HttpStatus.OK);
    }

    @PostMapping("/admin/paymentmethod")
    public ResponseEntity<PaymentMethodResponseDto> addPaymentMethod(@RequestBody PaymentMethodRequestDto paymentMethodRequestDto){
        return new ResponseEntity<>(paymentMethodService.addPaymentMethodTitle(paymentMethodRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/paymentmethod/{id}")
    public ResponseEntity<PaymentMethodResponseDto> editPaymentMethod(@PathVariable Long pmId,@RequestBody PaymentMethodRequestDto paymentMethodRequestDto){
        return new ResponseEntity<>(paymentMethodService.updatePaymentMethodTitle(pmId,paymentMethodRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/paymentmethod/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long pmId){
        paymentMethodService.deletePaymentMethodTitle(pmId);
        return ResponseEntity.ok().build();
    }

}