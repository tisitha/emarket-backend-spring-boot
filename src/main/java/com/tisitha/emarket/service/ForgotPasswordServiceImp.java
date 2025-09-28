package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.ChangePasswordDto;
import com.tisitha.emarket.dto.Mailbody;
import com.tisitha.emarket.exception.OtpExpiredException;
import com.tisitha.emarket.exception.OtpNotFoundException;
import com.tisitha.emarket.exception.PasswordNotMatchException;
import com.tisitha.emarket.exception.UserNotFoundException;
import com.tisitha.emarket.model.ForgotPassword;
import com.tisitha.emarket.model.User;
import com.tisitha.emarket.repo.ForgotPasswordRepository;
import com.tisitha.emarket.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImp implements ForgotPasswordService{

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void verifyEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        forgotPasswordRepository.deleteByUserId(user.getId());
        int otp = otpGenerator();
        Mailbody mailbody = Mailbody.builder()
                .to(email)
                .text("This is the OPT for your forgot password request "+otp)
                .subject("OTP for forgot password request")
                .build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+60*1000*5))
                .user(user)
                .build();
        emailService.sendSimpleMessage(mailbody);
        forgotPasswordRepository.save(fp);

    }

    @Override
    public void verifyOtp(Integer otp,String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException());
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new OtpNotFoundException());
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getId());
            throw new OtpNotFoundException();
        }
    }

    @Override
    @Transactional
    public void changePasswordHandler(ChangePasswordDto changePasswordDto, Integer otp, String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException());
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new OtpNotFoundException());
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getId());
            throw new OtpExpiredException();
        }
        if(!Objects.equals(changePasswordDto.password(),changePasswordDto.repeatPassword())){
            throw new PasswordNotMatchException();
        }
        String encodedPassword = passwordEncoder.encode(changePasswordDto.password());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        forgotPasswordRepository.deleteById(fp.getId());

    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100000,999999);
    }
}