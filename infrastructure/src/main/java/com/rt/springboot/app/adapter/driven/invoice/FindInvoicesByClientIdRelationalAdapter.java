package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicesByClientIdPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@DrivenAdapter
@RequiredArgsConstructor
public class FindInvoicesByClientIdRelationalAdapter implements FindInvoicesByClientIdPort {
    private final InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findInvoicesByClientId(UUID id) {
        return invoiceRepository.findByClientUuid(id).stream().map(InvoiceMapper.INSTANCE::toDomain).toList();
    }
}
