package com.rt.springboot.app.adapter.driving.dto;

import com.rt.springboot.app.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(uses = {CommonFieldsMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "uuid", target = "id", qualifiedByName = "uuidToString"),
            @Mapping(source = "price", target = "price", qualifiedByName = "bigDecimalToString"),
            @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
    })
    ProductDto toDto(Product product);
}
