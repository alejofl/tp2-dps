package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.CreateUserPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DrivenAdapter
@RequiredArgsConstructor
public class CreateUserRelationalAdapter implements CreateUserPort {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User create(String username, String password) {
        final var authority = new RoleRelationalEntity();
        authority.setAuthority("ROLE_USER");
        roleRepository.save(authority);
        final var user = new UserRelationalEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        user.setAuthorities(List.of(authority));
        userRepository.save(user);
        return UserMapper.INSTANCE.toDomain(user);
    }
}
