package com.rt.springboot.app.usecase.user;

import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.UserRepository;
import com.rt.springboot.app.port.driving.user.FindByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByUsernameUseCaseImpl implements FindByUsernameUseCase {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
