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
        if (item == null || item.getProduct() == null || item.getProduct().getPrice() == null) {
            return null;
        }
        final var subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getAmount()));
        return CommonFieldsMapper.bigDecimalToString(subtotal);
    }
}
