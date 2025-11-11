package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserRelationalAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private CreateUserRelationalAdapter adapter;

    @Test
    void shouldCreateUser() {
        String username = "john";
        String password = "1234";

        RoleRelationalEntity savedRole = new RoleRelationalEntity();
        savedRole.setId(1L);
        savedRole.setAuthority("ROLE_USER");

        UserRelationalEntity savedUser = new UserRelationalEntity();
        savedUser.setId(100L);
        savedUser.setUsername(username);
        savedUser.setPassword(password);
        savedUser.setEnabled(true);
        savedUser.setAuthorities(List.of(savedRole));

        when(roleRepository.save(any(RoleRelationalEntity.class)))
                .thenReturn(savedRole);

        when(userRepository.save(any(UserRelationalEntity.class)))
                .thenReturn(savedUser);

        User result = adapter.create(username, password);

        verify(roleRepository).save(any(RoleRelationalEntity.class));
        verify(userRepository).save(any(UserRelationalEntity.class));

        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo(username);
        assertThat(result.password()).isEqualTo(password);
        assertThat(result.enabled()).isTrue();
        assertThat(result.authorities().get(0).authority())
                .isEqualTo("ROLE_USER");
    }
}