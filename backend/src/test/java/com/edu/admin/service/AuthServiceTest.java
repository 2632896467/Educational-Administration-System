package com.edu.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.dto.auth.LoginRequest;
import com.edu.admin.dto.auth.LoginResponse;
import com.edu.admin.entity.Role;
import com.edu.admin.entity.User;
import com.edu.admin.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldTrimUsernameBeforeLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername("  admin  ");
        request.setPassword("123456");

        User user = User.builder()
                .id(1L)
                .username("admin")
                .password("123456")
                .realName("System Admin")
                .role(Role.ADMIN)
                .build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        LoginResponse response = authService.login(request);

        assertEquals(1L, response.getUserId());
        assertEquals("admin", response.getUsername());
    }

    @Test
    void shouldRejectWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrong-password");

        User user = User.builder()
                .id(1L)
                .username("admin")
                .password("123456")
                .realName("System Admin")
                .role(Role.ADMIN)
                .build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> authService.login(request));
    }
}
