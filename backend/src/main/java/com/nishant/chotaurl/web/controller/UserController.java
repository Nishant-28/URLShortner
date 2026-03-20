package com.nishant.chotaurl.web.controller;

import com.nishant.chotaurl.domain.models.CreateUserCmd;
import com.nishant.chotaurl.domain.models.Role;
import com.nishant.chotaurl.domain.services.UserService;
import com.nishant.chotaurl.web.dtos.RegisterUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody RegisterUserRequest registerRequest) {
        try {
            var cmd = new CreateUserCmd(
                    registerRequest.email(),
                    registerRequest.password(),
                    registerRequest.name(),
                    Role.ROLE_USER);

            userService.createUser(cmd);
            return ResponseEntity.ok("Registration successful! Please login.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + e.getMessage());
        }
    }
}