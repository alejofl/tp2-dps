package com.rt.springboot.app.adapter.driven.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.product.FindProductsByNamePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FindProductsByNameRelationalAdapter implements FindProductsByNamePort {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByName(String term) {
        return this.productRepository
                .findByNameContaining(term)
                .stream()
                .map(ProductMapper.INSTANCE::toDomain)
                .toList();
    }
}

