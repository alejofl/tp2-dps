package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.port.driven.invoice.DeleteInvoicePort;
import com.rt.springboot.app.port.driving.invoice.DeleteInvoiceUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteInvoiceUseCaseImpl implements DeleteInvoiceUseCase {

    private final DeleteInvoicePort deleteInvoicePort;

    @Override
    public void delete(UUID id) {
        this.deleteInvoicePort.delete(id);
    }
}
