package com.edu.admin.dto.auth;

import com.edu.admin.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long userId;
    private String username;
    private String realName;
    private Role role;
    private String token;
}

