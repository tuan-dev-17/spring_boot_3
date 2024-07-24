package com.dev.identity.service.configuration;

import com.dev.identity.service.entity.User;
import com.dev.identity.service.repository.UserRepository;
import com.dev.identity.service.utils.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                HashSet<String> roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .build();

                userRepository.save(admin);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
