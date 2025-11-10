package com.rt.springboot.app.adapter.driven.product;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
public class ProductRelationalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID uuid;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private Date createdAt;
}
