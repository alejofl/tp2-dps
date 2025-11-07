package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRelationalEntity, Long> {
    User findByUsername(String username);
}
