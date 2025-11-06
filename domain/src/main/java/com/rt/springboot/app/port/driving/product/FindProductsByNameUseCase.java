package com.rt.springboot.app.port.driving.product;

import com.rt.springboot.app.model.Product;

import java.util.List;

public interface FindProductsByNameUseCase {
    List<Product> findByName(String term);
}

