package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.LoginDto;
import com.tisitha.emarket.dto.UserRegisterDto;
import com.tisitha.emarket.dto.VendorRegisterDto;
import com.tisitha.emarket.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-user")
    public void registerUserAccount(@RequestBody UserRegisterDto userRegisterDto){
        userService.registerUserAccount(userRegisterDto);
    }

    @PostMapping("/register-vendor")
    public void registerVendorAccount(@RequestBody VendorRegisterDto vendorRegisterDto){
        userService.registerVendorAccount(vendorRegisterDto);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginAccount(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.loginAccount(loginDto), HttpStatus.OK);
    }

//    public String updateAccount()
//
//    public String deleteAccount()

}
