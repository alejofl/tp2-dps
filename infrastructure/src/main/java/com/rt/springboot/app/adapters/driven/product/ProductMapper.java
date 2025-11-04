package com.rt.springboot.app.adapters.driven.product;

import com.rt.springboot.app.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toDomain(ProductRelationalEntity entity);
}
