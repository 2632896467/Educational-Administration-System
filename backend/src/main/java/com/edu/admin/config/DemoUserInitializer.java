package com.edu.admin.config;

import com.edu.admin.entity.Role;
import com.edu.admin.entity.User;
import com.edu.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoUserInitializer implements ApplicationRunner {
    private static final String DEFAULT_PASSWORD = "123456";

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        // Keep the documented demo accounts available even if sql/init.sql was not executed.
        createIfMissing("admin", "System Admin", Role.ADMIN);
        createIfMissing("teacher01", "Teacher Zhang", Role.TEACHER);
        createIfMissing("student01", "Student Li", Role.STUDENT);
        createIfMissing("student02", "Student Wang", Role.STUDENT);
    }

    private void createIfMissing(String username, String realName, Role role) {
        if (userRepository.existsByUsername(username)) {
            return;
        }
        userRepository.save(User.builder()
                .username(username)
                .password(DEFAULT_PASSWORD)
                .realName(realName)
                .role(role)
                .build());
    }
}
