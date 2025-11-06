package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.client.ClientMapper;
import com.rt.springboot.app.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientMapper.class, InvoiceItemMapper.class})
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    Invoice toDomain(InvoiceRelationalEntity entity);
}
