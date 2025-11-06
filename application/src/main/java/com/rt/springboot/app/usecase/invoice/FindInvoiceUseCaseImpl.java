package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicePort;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FindInvoiceUseCaseImpl implements FindInvoiceUseCase {

    private final FindInvoicePort findInvoicePort;

    @Override
    public Invoice findById(UUID id) {
        return this.findInvoicePort.findById(id);
    }
}
