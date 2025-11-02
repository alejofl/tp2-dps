package com.rt.springboot.app.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Product {
    private String name;
    private BigDecimal price;
    private LocalDate createdAt;
}
