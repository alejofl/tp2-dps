package com.rt.springboot.app.port.driven;

import com.rt.springboot.app.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    Product findById(UUID id);

    List<Product> findByName(String term);

}
