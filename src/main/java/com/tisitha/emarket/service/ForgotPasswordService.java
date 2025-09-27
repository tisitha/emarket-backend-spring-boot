package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ChangePasswordDto;

public interface ForgotPasswordService {

    void verifyEmail(String email);

    void verifyOtp(Integer otp,String email);

    void changePasswordHandler(ChangePasswordDto changePasswordDto, Integer otp, String email);

}
