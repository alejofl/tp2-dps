package com.rt.springboot.app.usecase.product;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.product.FindProductPort;
import com.rt.springboot.app.port.driving.product.FindProductUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FindProductUseCaseImpl implements FindProductUseCase {

    private final FindProductPort findProductPort;

    @Override
    public Product findById(UUID id) {
        return this.findProductPort.findById(id);
    }
}
