package com.edu.admin.service;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.dto.auth.LoginRequest;
import com.edu.admin.dto.auth.LoginResponse;
import com.edu.admin.entity.User;
import com.edu.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(normalizeUsername(request.getUsername()))
                .filter(existingUser -> existingUser.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new BusinessException("Invalid username or password"));

        return LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .role(user.getRole())
                .token("mock-token-" + user.getId())
                .build();
    }

    private String normalizeUsername(String username) {
        return username == null ? null : username.trim();
    }
}
