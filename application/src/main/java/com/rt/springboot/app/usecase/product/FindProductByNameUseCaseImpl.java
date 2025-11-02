package com.rt.springboot.app.usecase.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.ProductRepository;
import com.rt.springboot.app.port.driving.product.FindProductByNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindProductByNameUseCaseImpl implements FindProductByNameUseCase {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByName(String term) {
        return this.productRepository.findByName(term);
    }
}
