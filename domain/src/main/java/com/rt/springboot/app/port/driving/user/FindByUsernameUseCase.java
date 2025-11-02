package com.rt.springboot.app.port.driving.user;

import com.rt.springboot.app.model.User;

public interface FindByUsernameUseCase {
    User findByUsername(String username);
}

