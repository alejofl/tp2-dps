package com.rt.springboot.app.model;

import java.time.LocalDate;
import java.util.UUID;

public record Client(
    UUID uuid,
    String firstName,
    String lastName,
    String email,
    String photo,
    LocalDate createdAt
) {}
