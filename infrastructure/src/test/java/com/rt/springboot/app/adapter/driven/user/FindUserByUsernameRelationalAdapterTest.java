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
class FindUserByUsernameRelationalAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByUsernameRelationalAdapter adapter;

    @Test
    void shouldFindUserByUsername() {
        String username = "john";

        RoleRelationalEntity role = new RoleRelationalEntity();
        role.setId(1L);
        role.setAuthority("ROLE_USER");

        UserRelationalEntity userEntity = new UserRelationalEntity();
        userEntity.setId(10L);
        userEntity.setUsername(username);
        userEntity.setPassword("1234");
        userEntity.setEnabled(true);
        userEntity.setAuthorities(List.of(role));

        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        User result = adapter.findByUsername(username);

        verify(userRepository).findByUsername(username);

        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo(username);
        assertThat(result.password()).isEqualTo("1234");
        assertThat(result.enabled()).isTrue();
        assertThat(result.authorities().get(0).authority()).isEqualTo("ROLE_USER");
    }
}
