package com.tisitha.emarket.util;

import com.tisitha.emarket.model.Role;
import com.tisitha.emarket.model.User;
import com.tisitha.emarket.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminAccountInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminAccountInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${admin.email}")String adminEmail, @Value("${admin.password}")String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User adminUser = new User();
            adminUser.setFname("Admin");
            adminUser.setLname("Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setPhoneNo("");
            adminUser.setAddress("");
            adminUser.setRole(Role.ROLE_ADMIN);
            userRepository.save(adminUser);
            log.info("Admin account created successfully.");
        } else {
            log.info("Admin account already exists. Skipping creation.");
        }
    }
}
