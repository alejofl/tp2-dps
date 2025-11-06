package com.rt.springboot.app.port.driven.product;

import com.rt.springboot.app.model.Product;

import java.util.List;

public interface FindProductsByNamePort {

    List<Product> findByName(String term);
}

