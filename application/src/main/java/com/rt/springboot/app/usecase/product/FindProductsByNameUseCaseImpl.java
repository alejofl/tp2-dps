package com.rt.springboot.app.usecase.product;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.product.FindProductsByNamePort;
import com.rt.springboot.app.port.driving.product.FindProductsByNameUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class FindProductsByNameUseCaseImpl implements FindProductsByNameUseCase {

    private final FindProductsByNamePort findProductsByNamePort;

    @Override
    public List<Product> findByName(String term) {
        return this.findProductsByNamePort.findByName(term);
    }
}
