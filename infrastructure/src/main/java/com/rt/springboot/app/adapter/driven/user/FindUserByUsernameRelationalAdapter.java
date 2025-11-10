package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.FindUserByUsernamePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DrivenAdapter
@RequiredArgsConstructor
public class FindUserByUsernameRelationalAdapter implements FindUserByUsernamePort {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User findByUsername(String username) {
        final var user = this.userRepository.findByUsername(username);
        return UserMapper.INSTANCE.toDomain(user);
    }
}
