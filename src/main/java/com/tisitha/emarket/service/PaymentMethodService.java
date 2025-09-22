package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.PaymentMethodRequestDto;
import com.tisitha.emarket.dto.PaymentMethodResponseDto;

import java.util.List;

public interface PaymentMethodService {

    List<PaymentMethodResponseDto> getPaymentMethodTitles();

    PaymentMethodResponseDto getPaymentMethodTitle(Long paymentMethodId);

    PaymentMethodResponseDto addPaymentMethodTitle(PaymentMethodRequestDto paymentMethodRequestDto);

    PaymentMethodResponseDto updatePaymentMethodTitle(Long paymentMethodId,PaymentMethodRequestDto paymentMethodRequestDto);

    void deletePaymentMethodTitle(Long paymentMethodId);

}
