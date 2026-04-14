package com.edu.admin.config;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edu.admin.entity.Role;
import com.edu.admin.entity.User;
import com.edu.admin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;

@ExtendWith(MockitoExtension.class)
class DemoUserInitializerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DemoUserInitializer demoUserInitializer;

    @Test
    void shouldCreateMissingDemoUsersOnStartup() throws Exception {
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(userRepository.existsByUsername("teacher01")).thenReturn(false);
        when(userRepository.existsByUsername("student01")).thenReturn(false);
        when(userRepository.existsByUsername("student02")).thenReturn(false);

        demoUserInitializer.run(new DefaultApplicationArguments(new String[0]));

        verify(userRepository).save(argThat(user ->
                matchesUser(user, "admin", "System Admin", Role.ADMIN)));
        verify(userRepository).save(argThat(user ->
                matchesUser(user, "teacher01", "Teacher Zhang", Role.TEACHER)));
        verify(userRepository).save(argThat(user ->
                matchesUser(user, "student01", "Student Li", Role.STUDENT)));
        verify(userRepository).save(argThat(user ->
                matchesUser(user, "student02", "Student Wang", Role.STUDENT)));
    }

    @Test
    void shouldSkipExistingDemoUsers() throws Exception {
        when(userRepository.existsByUsername("admin")).thenReturn(true);
        when(userRepository.existsByUsername("teacher01")).thenReturn(true);
        when(userRepository.existsByUsername("student01")).thenReturn(true);
        when(userRepository.existsByUsername("student02")).thenReturn(true);

        demoUserInitializer.run(new DefaultApplicationArguments(new String[0]));

        verify(userRepository, times(0)).save(org.mockito.ArgumentMatchers.any(User.class));
    }

    private boolean matchesUser(User user, String username, String realName, Role role) {
        return user != null
                && username.equals(user.getUsername())
                && "123456".equals(user.getPassword())
                && realName.equals(user.getRealName())
                && role == user.getRole();
    }
}
