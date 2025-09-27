package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.ForgotPassword;
import com.tisitha.emarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {

    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    void deleteByUserId(UUID userId);

}
