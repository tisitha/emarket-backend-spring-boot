package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {

    void registerUserAccount(UserRegisterDto userRegisterDto);

    void registerVendorAccount(VendorRegisterDto vendorRegisterDto);

    LoginResponseDto loginAccount(LoginDto loginDto);

    void updateUser(UserUpdateDTO userUpdateDTO, Authentication authentication);

    void updateVendor(VendorUpdateDto vendorUpdateDto, Authentication authentication);

    void userUpdateToVendor(UserToVendorUpdateDto userToVendorUpdateDto, Authentication authentication);

    void deleteUser(PasswordDTO pass, Authentication authentication);
}
