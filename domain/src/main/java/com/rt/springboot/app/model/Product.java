package com.rt.springboot.app.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Product {
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private LocalDate createdAt;
}
