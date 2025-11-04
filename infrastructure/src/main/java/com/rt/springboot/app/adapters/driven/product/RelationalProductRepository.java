package com.rt.springboot.app.adapters.driven.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RelationalProductRepository implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product findById(UUID id) {
        final var result = this.jpaProductRepository.findByUuid(id);
        return ProductMapper.INSTANCE.toDomain(result);
    }

    @Override
    public List<Product> findByName(String term) {
        return this.jpaProductRepository
                .findByNameContaining(term)
                .stream()
                .map(ProductMapper.INSTANCE::toDomain)
                .toList();
    }
}
