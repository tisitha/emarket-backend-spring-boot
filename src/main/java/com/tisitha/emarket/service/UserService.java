package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.LoginDto;
import com.tisitha.emarket.dto.UserRegisterDto;
import com.tisitha.emarket.dto.VendorRegisterDto;

public interface UserService {

    void registerUserAccount(UserRegisterDto userRegisterDto);

    void registerVendorAccount(VendorRegisterDto vendorRegisterDto);

    String loginAccount(LoginDto loginDto);

}
