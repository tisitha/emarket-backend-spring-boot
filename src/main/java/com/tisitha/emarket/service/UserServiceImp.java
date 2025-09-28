package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.exception.*;
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

import java.util.Optional;
import java.util.UUID;

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
            throw new PasswordNotMatchException();
        }
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw new EmailTakenException();
        }
        Province province = provinceRepository.findById(userRegisterDto.getProvinceId()).orElseThrow(()->new ProvinceNotFoundException());
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
            throw new PasswordNotMatchException();
        }
        if(userRepository.existsByEmail(vendorRegisterDto.getEmail())){
            throw new UserNotFoundException();
        }
        Province province = provinceRepository.findById(vendorRegisterDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
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
        vendorProfile.setBusinessName(vendorRegisterDto.getBusinessName());
        vendorProfile.setBankAccountNo(vendorRegisterDto.getBankAccountNo());
        vendorProfile.setBank(vendorRegisterDto.getBank());
        vendorProfileRepository.save(vendorProfile);
    }

    @Override
    public String loginAccount(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        return jwtUtil.generateToken(loginDto.getEmail());
    }

    @Override
    public void updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),userUpdateDTO.getCurrentPassword()));
        if(!authentication.isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        if(userRepository.existsByEmail(userUpdateDTO.getEmail()) && userUpdateDTO.getEmail() != null){
            throw new EmailTakenException();
        }
        if(!userUpdateDTO.getPassword().equals(userUpdateDTO.getPasswordRepeat())){
            throw new PasswordNotMatchException();
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        Optional.ofNullable(userUpdateDTO.getFname()).ifPresent(user::setFname);
        Optional.ofNullable(userUpdateDTO.getLname()).ifPresent(user::setLname);
        Optional.ofNullable(userUpdateDTO.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdateDTO.getPassword()).ifPresent((pass)->user.setPassword(passwordEncoder.encode(pass)));
        Optional.ofNullable(userUpdateDTO.getPhoneNo()).ifPresent(user::setPhoneNo);
        Optional.ofNullable(userUpdateDTO.getAddress()).ifPresent(user::setAddress);
        Optional<Province> province = provinceRepository.findById(userUpdateDTO.getProvinceId());
        province.ifPresent(user::setProvince);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateVendor(UUID id, VendorUpdateDto vendorUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        VendorProfile vendorProfile = vendorProfileRepository.findById(id).orElseThrow(InvalidInputException::new);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),vendorUpdateDto.getCurrentPassword()));
        if(!authentication.isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        if(userRepository.existsByEmail(vendorUpdateDto.getEmail()) && vendorUpdateDto.getEmail() != null){
            throw new EmailTakenException();
        }
        if(!vendorUpdateDto.getPassword().equals(vendorUpdateDto.getPasswordRepeat())){
            throw new PasswordNotMatchException();
        }
        if (vendorUpdateDto.getEmail() != null) {
            user.setEmail(vendorUpdateDto.getEmail());
        }
        Optional.ofNullable(vendorUpdateDto.getFname()).ifPresent(user::setFname);
        Optional.ofNullable(vendorUpdateDto.getLname()).ifPresent(user::setLname);
        Optional.ofNullable(vendorUpdateDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(vendorUpdateDto.getPassword()).ifPresent((pass)->user.setPassword(passwordEncoder.encode(pass)));
        Optional.ofNullable(vendorUpdateDto.getPhoneNo()).ifPresent(user::setPhoneNo);
        Optional.ofNullable(vendorUpdateDto.getAddress()).ifPresent(user::setAddress);
        Optional<Province> province = provinceRepository.findById(vendorUpdateDto.getProvinceId());
        province.ifPresent(user::setProvince);
        userRepository.save(user);
        Optional.ofNullable(vendorUpdateDto.getBusinessName()).ifPresent(vendorProfile::setBusinessName);
        Optional.ofNullable(vendorUpdateDto.getBankAccountNo()).ifPresent(vendorProfile::setBankAccountNo);
        Optional.ofNullable(vendorUpdateDto.getBank()).ifPresent(vendorProfile::setBank);
        vendorProfileRepository.save(vendorProfile);
    }

    @Override
    public void deleteUser(UUID id, PasswordDTO pass) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),pass.password()));
        if(!authentication.isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        userRepository.deleteById(user.getId());
    }
}
