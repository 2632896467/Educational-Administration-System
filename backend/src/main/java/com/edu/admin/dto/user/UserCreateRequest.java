package com.edu.admin.dto.user;

import com.edu.admin.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Real name cannot be blank")
    private String realName;

    @NotNull(message = "Role is required")
    private Role role;
}

