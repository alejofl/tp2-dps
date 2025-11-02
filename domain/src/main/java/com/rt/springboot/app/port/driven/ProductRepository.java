package com.rt.springboot.app.port.driven;

import com.rt.springboot.app.model.Product;

import java.util.List;

public interface ProductRepository {

    Product findById(long id);

    List<Product> findByName(String term);


}
