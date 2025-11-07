package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.FindUserByUsernamePort;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class FindUserByUsernameRelationalAdapter implements FindUserByUsernamePort {
    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
