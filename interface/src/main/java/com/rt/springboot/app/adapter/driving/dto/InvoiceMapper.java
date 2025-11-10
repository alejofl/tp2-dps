package com.rt.springboot.app.adapter.driving.dto;

import com.rt.springboot.app.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(uses = {ClientMapper.class, InvoiceItemMapper.class, CommonFieldsMapper.class})
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mappings({
            @Mapping(source = "uuid", target = "id", qualifiedByName = "uuidToString"),
            @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "total", expression = "java(calculateTotal(invoice))")
    })
    InvoiceDto toDto(Invoice invoice);

    default String calculateTotal(Invoice invoice) {
        if (invoice == null || invoice.items() == null) {
            return null;
        }

        final var total = invoice.items()
                .stream()
                .filter(i -> i.product() != null && i.product().price() != null)
                .map(i -> i.product().price().multiply(BigDecimal.valueOf(i.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CommonFieldsMapper.bigDecimalToString(total);
    }
}
