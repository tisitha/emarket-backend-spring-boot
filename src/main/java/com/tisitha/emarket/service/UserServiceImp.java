package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.LoginDto;
import com.tisitha.emarket.dto.UserRegisterDto;
import com.tisitha.emarket.dto.VendorRegisterDto;
import com.tisitha.emarket.model.Province;
import com.tisitha.emarket.model.Role;
import com.tisitha.emarket.model.User;
import com.tisitha.emarket.model.VendorProfile;
import com.tisitha.emarket.repo.ProvinceRepository;
import com.tisitha.emarket.repo.UserRepository;
import com.tisitha.emarket.repo.VendorProfileRepository;
import com.tisitha.emarket.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProvinceRepository provinceRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public void registerUserAccount(UserRegisterDto userRegisterDto) {
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getPasswordRepeat())){
            throw new RuntimeException("");
        }
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw new RuntimeException("");
        }
        Province province = provinceRepository.findById(userRegisterDto.getProvinceId()).orElseThrow(()->new RuntimeException(""));
        User user = new User();
        user.setFname(userRegisterDto.getFname());
        user.setLname(userRegisterDto.getLname());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setPhoneNo(userRegisterDto.getPhoneNo());
        user.setAddress(userRegisterDto.getAddress());
        user.setRole(Role.USER);
        user.setProvince(province);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void registerVendorAccount(VendorRegisterDto vendorRegisterDto) {
        if(!vendorRegisterDto.getPassword().equals(vendorRegisterDto.getPasswordRepeat())){
            throw new RuntimeException("");
        }
        if(userRepository.existsByEmail(vendorRegisterDto.getEmail())){
            throw new RuntimeException("");
        }
        Province province = provinceRepository.findById(vendorRegisterDto.getProvinceId()).orElseThrow(()->new RuntimeException(""));
        User user = new User();
        user.setFname(vendorRegisterDto.getFname());
        user.setLname(vendorRegisterDto.getLname());
        user.setEmail(vendorRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(vendorRegisterDto.getPassword()));
        user.setPhoneNo(vendorRegisterDto.getPhoneNo());
        user.setAddress(vendorRegisterDto.getAddress());
        user.setRole(Role.VENDOR);
        user.setProvince(province);
        User newUser = userRepository.save(user);
        System.out.println(vendorRegisterDto);
        VendorProfile vendorProfile = new VendorProfile();
        vendorProfile.setUser(newUser);
        vendorProfile.setBusinessName(vendorRegisterDto.getVendorProfileDto().getBusinessName());
        vendorProfile.setBankAccountNo(vendorRegisterDto.getVendorProfileDto().getBankAccountNo());
        vendorProfile.setBank(vendorRegisterDto.getVendorProfileDto().getBank());
        vendorProfileRepository.save(vendorProfile);
    }

    @Override
    public String loginAccount(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new RuntimeException("");
        }
        return jwtUtil.generateToken(loginDto.getEmail());
    }
}
