package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DrivenAdapter
@RequiredArgsConstructor
public class FindInvoiceRelationalAdapter implements FindInvoicePort {

    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public Invoice findById(UUID id) {
        final var result = this.invoiceRepository.findByUuid(id);
        return InvoiceMapper.INSTANCE.toDomain(result);
    }
}

