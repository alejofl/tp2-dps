package com.rt.springboot.app.port.driven;

import com.rt.springboot.app.model.User;

public interface UserRepository {

    User findByUsername(String username);
}
