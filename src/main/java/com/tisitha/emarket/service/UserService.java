package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import org.springframework.security.core.Authentication;

public interface UserService {

    AccountResponseDto getUser(Authentication authentication);

    void registerUserAccount(UserRegisterDto userRegisterDto);

    void registerVendorAccount(VendorRegisterDto vendorRegisterDto);

    LoginResponseDto loginAccount(LoginDto loginDto);

    void updateUser(UserUpdateDTO userUpdateDTO, Authentication authentication);

    void updateVendor(VendorUpdateDto vendorUpdateDto, Authentication authentication);

    void userUpdateToVendor(UserToVendorUpdateDto userToVendorUpdateDto, Authentication authentication);

    void vendorUpdateToUser(PasswordDTO passwordDTO, Authentication authentication);

    void updatePassword(NewPasswordRequestDto newPasswordRequestDto, Authentication authentication);

    void deleteUser(PasswordDTO passwordDTO, Authentication authentication);
}
