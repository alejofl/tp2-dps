package com.rt.springboot.app.port.driven.product;

import com.rt.springboot.app.model.Product;

import java.util.UUID;

public interface FindProductPort {

    Product findById(UUID id);
}

