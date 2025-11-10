package com.rt.springboot.app.adapter.driving.dto;

import com.rt.springboot.app.model.InvoiceItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(uses = ProductMapper.class)
public interface InvoiceItemMapper {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    @Mapping(target = "subtotal", expression = "java(calculateSubtotal(item))")
    InvoiceItemDto toDto(InvoiceItem item);

    default String calculateSubtotal(InvoiceItem item) {
        if (item == null || item.product() == null || item.product().price() == null) {
            return null;
        }
        final var subtotal = item.product().price().multiply(BigDecimal.valueOf(item.amount()));
        return CommonFieldsMapper.bigDecimalToString(subtotal);
    }
}
