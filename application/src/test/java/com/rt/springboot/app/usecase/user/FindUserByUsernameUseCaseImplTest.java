package com.rt.springboot.app.usecase.user;

import com.rt.springboot.app.model.Role;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.FindUserByUsernamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUserByUsernameUseCaseImplTest {

    @Mock
    private FindUserByUsernamePort findUserByUsernamePort;

    @InjectMocks
    private FindUserByUsernameUseCaseImpl findUserByUsernameUseCase;

    private String username;
    private String password;
    private List<Role> authorities;

    @BeforeEach
    void setUp() {
        this.username = "testuser";
        this.password = "$2a$10$encodedPasswordHash";
        this.authorities = new ArrayList<>();
    }

    @Test
    @DisplayName("Should find user by username when user exists")
    void shouldFindUserByUsernameWhenUserExists() {
        // Given
        User expectedUser = new User(username, password, true, authorities);
        when(findUserByUsernamePort.findByUsername(username)).thenReturn(expectedUser);

        // When
        User result = findUserByUsernameUseCase.findByUsername(username);

        // Then
        assertNotNull(result);
        assertEquals(expectedUser.username(), result.username());
        assertEquals(password, result.password());
        assertEquals(expectedUser.enabled(), result.enabled());
        assertEquals(authorities, result.authorities());
    }

}

