package com.rt.springboot.app.adapter.driven.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.product.FindProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FindProductRelationalAdapter implements FindProductPort {

    private final ProductRepository productRepository;

    @Override
    public Product findById(UUID id) {
        final var result = this.productRepository.findByUuid(id);
        return ProductMapper.INSTANCE.toDomain(result);
    }
}

