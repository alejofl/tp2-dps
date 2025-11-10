package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.port.driven.invoice.DeleteInvoicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DrivenAdapter
@RequiredArgsConstructor
public class DeleteInvoiceRelationalAdapter implements DeleteInvoicePort {

    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public void delete(UUID id) {
        this.invoiceRepository.deleteByUuid(id);
    }
}

