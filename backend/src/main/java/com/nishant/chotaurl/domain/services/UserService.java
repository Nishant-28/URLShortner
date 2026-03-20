package com.nishant.chotaurl.domain.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nishant.chotaurl.domain.entities.User;
import com.nishant.chotaurl.domain.models.CreateUserCmd;
import com.nishant.chotaurl.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void createUser(CreateUserCmd cmd) {
        if (userRepository.existsByEmail(cmd.email())) {
            throw new RuntimeException("Email already exists");
        }
        var user = new User();
        user.setEmail(cmd.email());
        user.setPassword(passwordEncoder.encode(cmd.password()));
        user.setName(cmd.name());
        user.setRole(cmd.role());
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
