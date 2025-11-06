package com.rt.springboot.app.port.driven.user;

import com.rt.springboot.app.model.User;

public interface FindUserByUsernamePort {

    User findByUsername(String username);
}

