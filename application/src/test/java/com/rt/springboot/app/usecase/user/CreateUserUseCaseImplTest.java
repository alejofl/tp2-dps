package com.rt.springboot.app.usecase.user;

import com.rt.springboot.app.model.Role;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.CreateUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseImplTest {

    @Mock
    private CreateUserPort createUserPort;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    private String username;
    private String password;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        this.username = "testuser";
        this.password = "password123";
        this.encodedPassword = "$2a$10$encodedPasswordHash";
    }

    @Test
    @DisplayName("Should create user successfully with valid data")
    void shouldCreateUserSuccessfully() {
        // Given
        List<Role> authorities = new ArrayList<>();
        User expectedUser = new User(username, encodedPassword, true, authorities);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(createUserPort.create(eq(username), eq(encodedPassword))).thenReturn(expectedUser);

        // When
        User result = createUserUseCase.create(username, password);

        // Then
        assertNotNull(result);
        assertEquals(expectedUser.username(), result.username());
        assertEquals(encodedPassword, result.password());
        assertEquals(expectedUser.enabled(), result.enabled());
    }

}

