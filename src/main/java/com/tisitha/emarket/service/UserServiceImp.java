package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.exception.*;
import com.tisitha.emarket.model.*;
import com.tisitha.emarket.repo.ProvinceRepository;
import com.tisitha.emarket.repo.UserRepository;
import com.tisitha.emarket.repo.VendorProfileRepository;
import com.tisitha.emarket.util.JWTUtil;
import com.tisitha.emarket.util.ObjectConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public AccountResponseDto getUser(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        return ObjectConverter.mapAccountToAccountDto(user);
    }

    @Override
    public void registerUserAccount(UserRegisterDto userRegisterDto) {
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getPasswordRepeat())){
            throw new PasswordNotMatchException();
        }
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw new EmailTakenException();
        }
        Province province = provinceRepository.findById(userRegisterDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
        User user = new User();
        user.setFname(userRegisterDto.getFname());
        user.setLname(userRegisterDto.getLname());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setPhoneNo(userRegisterDto.getPhoneNo());
        user.setAddress(userRegisterDto.getAddress());
        user.setRole(Role.ROLE_USER);
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
            throw new EmailTakenException();
        }
        Province province = provinceRepository.findById(vendorRegisterDto.getProvinceId()).orElseThrow(ProvinceNotFoundException::new);
        User user = new User();
        user.setFname(vendorRegisterDto.getFname());
        user.setLname(vendorRegisterDto.getLname());
        user.setEmail(vendorRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(vendorRegisterDto.getPassword()));
        user.setPhoneNo(vendorRegisterDto.getPhoneNo());
        user.setAddress(vendorRegisterDto.getAddress());
        user.setRole(Role.ROLE_VENDOR);
        user.setProvince(province);
        User newUser = userRepository.save(user);
        VendorProfile vendorProfile = new VendorProfile();
        vendorProfile.setUser(newUser);
        vendorProfile.setBusinessName(vendorRegisterDto.getBusinessName());
        vendorProfile.setBankAccountNo(vendorRegisterDto.getBankAccountNo());
        vendorProfile.setBank(vendorRegisterDto.getBank());
        vendorProfileRepository.save(vendorProfile);
    }

    @Override
    public LoginResponseDto loginAccount(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        String accessToken = jwtUtil.generateToken(loginDto.getEmail());
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(UnauthorizeAccessException::new);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setId(user.getId());
        loginResponseDto.setName(user.getFname());
        loginResponseDto.setRole(user.getRole().name());
        loginResponseDto.setToken(accessToken);
        return loginResponseDto;
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().equals(authentication.getName()) && userRepository.existsByEmail(userUpdateDTO.getEmail())){
            throw new EmailTakenException();
        }
        Optional.ofNullable(userUpdateDTO.getFname()).ifPresent(user::setFname);
        Optional.ofNullable(userUpdateDTO.getLname()).ifPresent(user::setLname);
        Optional.ofNullable(userUpdateDTO.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdateDTO.getPhoneNo()).ifPresent(user::setPhoneNo);
        Optional.ofNullable(userUpdateDTO.getAddress()).ifPresent(user::setAddress);
        if(userUpdateDTO.getProvinceId()!=null) {
            Optional<Province> province = provinceRepository.findById(userUpdateDTO.getProvinceId());
            province.ifPresent(user::setProvince);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateVendor(VendorUpdateDto vendorUpdateDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        VendorProfile vendorProfile = vendorProfileRepository.findById(user.getId()).orElseThrow(InvalidInputException::new);
        if(userRepository.existsByEmail(vendorUpdateDto.getEmail()) && !vendorUpdateDto.getEmail().equals(authentication.getName()) && vendorUpdateDto.getEmail() != null){
            throw new EmailTakenException();
        }
        if (vendorUpdateDto.getEmail() != null) {
            user.setEmail(vendorUpdateDto.getEmail());
        }
        Optional.ofNullable(vendorUpdateDto.getFname()).ifPresent(user::setFname);
        Optional.ofNullable(vendorUpdateDto.getLname()).ifPresent(user::setLname);
        Optional.ofNullable(vendorUpdateDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(vendorUpdateDto.getPhoneNo()).ifPresent(user::setPhoneNo);
        Optional.ofNullable(vendorUpdateDto.getAddress()).ifPresent(user::setAddress);
        if(vendorUpdateDto.getProvinceId()!=null) {
            Optional<Province> province = provinceRepository.findById(vendorUpdateDto.getProvinceId());
            province.ifPresent(user::setProvince);
        }
        userRepository.save(user);
        Optional.ofNullable(vendorUpdateDto.getBusinessName()).ifPresent(vendorProfile::setBusinessName);
        Optional.ofNullable(vendorUpdateDto.getBankAccountNo()).ifPresent(vendorProfile::setBankAccountNo);
        Optional.ofNullable(vendorUpdateDto.getBank()).ifPresent(vendorProfile::setBank);
        vendorProfileRepository.save(vendorProfile);
    }

    @Override
    @Transactional
    public void userUpdateToVendor(UserToVendorUpdateDto userToVendorUpdateDto, Authentication authentication) {
        if(!authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(),userToVendorUpdateDto.getCurrentPassword())).isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        User user = (User) authentication.getPrincipal();
        if(vendorProfileRepository.existsById(user.getId())){
            throw new InvalidInputException();
        }
        VendorProfile vendorProfile = new VendorProfile();
        vendorProfile.setVendorId(user.getId());
        vendorProfile.setBusinessName(userToVendorUpdateDto.getBusinessName());
        vendorProfile.setBankAccountNo(userToVendorUpdateDto.getBankAccountNo());
        vendorProfile.setBank(userToVendorUpdateDto.getBank());
        user.setRole(Role.ROLE_VENDOR);
        user.setVendorProfile(vendorProfile);
        vendorProfile.setUser(userRepository.save(user));
        vendorProfileRepository.save(vendorProfile);
    }

    @Override
    @Transactional
    public void vendorUpdateToUser(PasswordDTO passwordDTO, Authentication authentication) {
        if(!authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(),passwordDTO.password())).isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        User user = (User) authentication.getPrincipal();
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        vendorProfileRepository.deleteById(user.getId());
    }

    @Override
    public void updatePassword(NewPasswordRequestDto newPasswordRequestDto, Authentication authentication) {
        if(!authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(),newPasswordRequestDto.getCurrentPassword())).isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        User user = (User) authentication.getPrincipal();
        if(!newPasswordRequestDto.getPassword().equals(newPasswordRequestDto.getPasswordRepeat())){
            throw new PasswordNotMatchException();
        }
        user.setPassword(newPasswordRequestDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(PasswordDTO passwordDTO, Authentication authentication) {
        if(!authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(),passwordDTO.password())).isAuthenticated()){
            throw new UnauthorizeAccessException();
        }
        User user = (User) authentication.getPrincipal();
        userRepository.deleteById(user.getId());
    }
}
