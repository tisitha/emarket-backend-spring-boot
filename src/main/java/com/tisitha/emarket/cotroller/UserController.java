package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.service.ForgotPasswordService;
import com.tisitha.emarket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ForgotPasswordService forgotPasswordService;

    public UserController(UserService userService, ForgotPasswordService forgotPasswordService) {
        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @GetMapping("/user/profile")
    public ResponseEntity<AccountResponseDto> getUser(Authentication authentication){
        return new ResponseEntity<>(userService.getUser(authentication),HttpStatus.OK);
    }

    @PostMapping("/auth/register-user")
    public ResponseEntity<Void> registerUserAccount(@Valid @RequestBody UserRegisterDto userRegisterDto){
        userService.registerUserAccount(userRegisterDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/auth/register-vendor")
    public ResponseEntity<Void> registerVendorAccount(@Valid @RequestBody VendorRegisterDto vendorRegisterDto){
        userService.registerVendorAccount(vendorRegisterDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> loginAccount(@Valid @RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.loginAccount(loginDto), HttpStatus.OK);
    }

    @PostMapping("/auth/verifymail/{email}")
    public ResponseEntity<Void> verifyEmail(@PathVariable String email){
        forgotPasswordService.verifyEmail(email);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/auth/varifyotp/{otp}/{email}")
    public ResponseEntity<Void> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
        forgotPasswordService.verifyOtp(otp,email);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/auth/changepassword/{otp}/{email}")
    public ResponseEntity<Void> changePasswordHandler(@Valid @RequestBody ChangePasswordDto changePasswordDto, @PathVariable Integer otp, @PathVariable String email){
        forgotPasswordService.changePasswordHandler(changePasswordDto,otp,email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/user-update")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, Authentication authentication) {
        userService.updateUser(userUpdateDTO,authentication);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/vendor-update")
    public ResponseEntity<Void> updateVendor(@Valid @RequestBody VendorUpdateDto vendorUpdateDto, Authentication authentication) {
        userService.updateVendor(vendorUpdateDto,authentication);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/usertovendor-update")
    public ResponseEntity<Void> updateUserToVendor(@Valid @RequestBody UserToVendorUpdateDto userToVendorUpdateDto, Authentication authentication) {
        userService.userUpdateToVendor(userToVendorUpdateDto,authentication);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/user-delete")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody PasswordDTO pass, Authentication authentication) {
        userService.deleteUser(pass,authentication);
        return ResponseEntity.noContent().build();
    }

}
