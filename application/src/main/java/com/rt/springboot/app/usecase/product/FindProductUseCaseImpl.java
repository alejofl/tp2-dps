package com.rt.springboot.app.usecase.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.ProductRepository;
import com.rt.springboot.app.port.driving.product.FindProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindProductUseCaseImpl implements FindProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product findById(UUID id) {
        return this.productRepository.findById(id);
    }
}
