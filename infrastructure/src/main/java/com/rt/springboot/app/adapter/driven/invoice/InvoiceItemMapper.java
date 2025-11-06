package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.product.ProductMapper;
import com.rt.springboot.app.model.InvoiceItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public interface InvoiceItemMapper {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    InvoiceItem toDomain(InvoiceItemRelationalEntity entity);
}
