package com.rt.springboot.app.model;

import java.util.List;

public record User(
    String username,
    String password,
    boolean enabled,
    List<Role> authorities
) {}
