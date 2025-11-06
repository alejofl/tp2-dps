package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Adapter
@RequiredArgsConstructor
public class FindInvoiceRelationalAdapter implements FindInvoicePort {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice findById(UUID id) {
        final var result = this.invoiceRepository.findByUuid(id);
        return InvoiceMapper.INSTANCE.toDomain(result);
    }
}

