package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;

import java.util.UUID;

public interface UserService {

    void registerUserAccount(UserRegisterDto userRegisterDto);

    void registerVendorAccount(VendorRegisterDto vendorRegisterDto);

    String loginAccount(LoginDto loginDto);

    void updateUser(UUID id, UserUpdateDTO userUpdateDTO);

    void updateVendor(UUID id, VendorUpdateDto vendorUpdateDto);

    void deleteUser(UUID id, PasswordDTO pass);
}
