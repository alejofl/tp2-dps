package com.rt.springboot.app.usecase.user;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driven.user.CreateUserPort;
import com.rt.springboot.app.port.driving.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final CreateUserPort createUserPort;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User create(String username, String password) {
        return this.createUserPort.create(username, passwordEncoder.encode(password));
    }
}
