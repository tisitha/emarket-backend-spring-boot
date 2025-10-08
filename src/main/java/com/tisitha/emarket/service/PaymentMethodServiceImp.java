package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.PaymentMethodRequestDto;
import com.tisitha.emarket.dto.PaymentMethodResponseDto;
import com.tisitha.emarket.exception.PaymentMethodNotFoundException;
import com.tisitha.emarket.model.PaymentMethod;
import com.tisitha.emarket.repo.PaymentMethodRepository;
import com.tisitha.emarket.util.ObjectConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImp implements PaymentMethodService{

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImp(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<PaymentMethodResponseDto> getPaymentMethodTitles() {
        List<PaymentMethod> warranties = paymentMethodRepository.findAll();
        return warranties.stream().map(ObjectConverter::mapPaymentMethodToPaymentMethodDto).toList();
    }

    @Override
    public PaymentMethodResponseDto getPaymentMethodTitle(Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(PaymentMethodNotFoundException::new);
        return ObjectConverter.mapPaymentMethodToPaymentMethodDto(paymentMethod);
    }

    @Override
    public PaymentMethodResponseDto addPaymentMethodTitle(PaymentMethodRequestDto paymentMethodRequestDto) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(paymentMethodRequestDto.getName());
        PaymentMethod newPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return ObjectConverter.mapPaymentMethodToPaymentMethodDto(newPaymentMethod);
    }

    @Override
    public PaymentMethodResponseDto updatePaymentMethodTitle(Long paymentMethodId,PaymentMethodRequestDto paymentMethodRequestDto) {
        PaymentMethod oldPaymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(PaymentMethodNotFoundException::new);
        oldPaymentMethod.setName(paymentMethodRequestDto.getName());
        PaymentMethod newPaymentMethod = paymentMethodRepository.save(oldPaymentMethod);
        return ObjectConverter.mapPaymentMethodToPaymentMethodDto(newPaymentMethod);
    }

    @Override
    public void deletePaymentMethodTitle(Long paymentMethodId) {
        paymentMethodRepository.findById(paymentMethodId).orElseThrow(PaymentMethodNotFoundException::new);
        paymentMethodRepository.deleteById(paymentMethodId);
    }

}
