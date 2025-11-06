package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.port.driven.invoice.DeleteInvoicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Adapter
@RequiredArgsConstructor
public class DeleteInvoiceRelationalAdapter implements DeleteInvoicePort {

    private final InvoiceRepository invoiceRepository;

    @Override
    public void delete(UUID id) {
        this.invoiceRepository.deleteByUuid(id);
    }
}

