package com.rt.springboot.app.usecase.user;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.FindUserByUsernamePort;
import com.rt.springboot.app.port.driving.user.FindByUsernameUseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindByUsernameUseCaseImpl implements FindByUsernameUseCase {

    private final FindUserByUsernamePort findUserByUsernamePort;

    @Override
    public User findByUsername(String username) {
        return this.findUserByUsernamePort.findByUsername(username);
    }
}
