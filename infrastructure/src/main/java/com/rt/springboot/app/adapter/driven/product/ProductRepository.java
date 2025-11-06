package com.rt.springboot.app.adapter.driven.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductRelationalEntity, Long> {
    ProductRelationalEntity findByUuid(UUID uuid);

    List<ProductRelationalEntity> findByNameContaining(String name);
}
