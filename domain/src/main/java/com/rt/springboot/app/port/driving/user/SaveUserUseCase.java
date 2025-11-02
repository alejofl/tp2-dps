package com.rt.springboot.app.port.driving.user;

import com.rt.springboot.app.model.User;

public interface SaveUserUseCase {
    User save(User registration);
}

