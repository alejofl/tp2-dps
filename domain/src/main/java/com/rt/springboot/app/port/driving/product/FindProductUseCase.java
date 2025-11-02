package com.rt.springboot.app.port.driving.product;

import com.rt.springboot.app.model.Product;

public interface FindProductUseCase {
    Product findById(Long id);
}

