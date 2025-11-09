package com.rt.springboot.app.port.driven.user;

import com.rt.springboot.app.model.User;

public interface CreateUserPort {
    User create(String username, String password);
}
