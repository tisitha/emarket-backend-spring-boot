package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.service.ForgotPasswordService;
import com.tisitha.emarket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ForgotPasswordService forgotPasswordService;

    public UserController(UserService userService, ForgotPasswordService forgotPasswordService) {
        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/auth/register-user")
    public void registerUserAccount(@Valid @RequestBody UserRegisterDto userRegisterDto){
        userService.registerUserAccount(userRegisterDto);
    }

    @PostMapping("/auth/register-vendor")
    public void registerVendorAccount(@Valid @RequestBody VendorRegisterDto vendorRegisterDto){
        userService.registerVendorAccount(vendorRegisterDto);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<String> loginAccount(@Valid @RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.loginAccount(loginDto), HttpStatus.OK);
    }

    @PostMapping("/auth/verifymail/{email}")
    public ResponseEntity<Void> verifyEmail(@PathVariable String email){
        forgotPasswordService.verifyEmail(email);
        return ResponseEntity.ok().build();

    }
    @PostMapping("/auth/varifyotp/{otp}/{email}")
    public ResponseEntity<Void> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
        forgotPasswordService.verifyOtp(otp,email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/changepassword/{otp}/{email}")
    public ResponseEntity<Void> changePasswordHandler(@Valid @RequestBody ChangePasswordDto changePasswordDto, @PathVariable Integer otp, @PathVariable String email){
        forgotPasswordService.changePasswordHandler(changePasswordDto,otp,email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/user-update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, Authentication authentication) {
        userService.updateUser(userUpdateDTO,authentication);
        return new ResponseEntity<>("successfully account updated",HttpStatus.OK);
    }

    @PutMapping("/user/vendor-update")
    public ResponseEntity<String> updateVendor(@Valid @RequestBody VendorUpdateDto vendorUpdateDto, Authentication authentication) {
        userService.updateVendor(vendorUpdateDto,authentication);
        return new ResponseEntity<>("successfully account updated",HttpStatus.OK);
    }

    @PutMapping("/user/usertovendor-update")
    public ResponseEntity<String> updateUserToVendor(@Valid @RequestBody UserToVendorUpdateDto userToVendorUpdateDto, Authentication authentication) {
        userService.userUpdateToVendor(userToVendorUpdateDto,authentication);
        return new ResponseEntity<>("successfully account updated",HttpStatus.OK);
    }

    @DeleteMapping("/user/user-delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody PasswordDTO pass, Authentication authentication) {
        userService.deleteUser(pass,authentication);
        return new ResponseEntity<>("successfully account deleted",HttpStatus.OK);
    }

}
