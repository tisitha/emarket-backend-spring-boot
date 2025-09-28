package com.tisitha.emarket.util;

import com.tisitha.emarket.model.Role;
import com.tisitha.emarket.model.User;
import com.tisitha.emarket.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminAccountInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminAccountInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        String adminEmail = "admin@admin.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User adminUser = new User();
            adminUser.setFname("Admin");
            adminUser.setLname("Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("adminadmin"));
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
