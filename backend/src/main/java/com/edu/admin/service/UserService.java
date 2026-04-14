package com.edu.admin.service;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.dto.user.UserCreateRequest;
import com.edu.admin.entity.User;
import com.edu.admin.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserCreateRequest request) {
        String username = normalize(request.getUsername());
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException("Username already exists");
        }
        User user = User.builder()
                .username(username)
                .password(request.getPassword())
                .realName(normalize(request.getRealName()))
                .role(request.getRole())
                .build();
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
