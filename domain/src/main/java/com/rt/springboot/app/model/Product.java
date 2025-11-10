package com.rt.springboot.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record Product(
    UUID uuid,
    String name,
    BigDecimal price,
    LocalDate createdAt
) {}
