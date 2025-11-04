package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.InvoiceRepository;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindInvoiceUseCaseImpl implements FindInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice findById(UUID id) {
        return this.invoiceRepository.findById(id);
    }
}
