package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicesByClientIdPort;
import com.rt.springboot.app.port.driving.invoice.FindInvoicesForClientUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class FindInvoicesForClientUseCaseImpl implements FindInvoicesForClientUseCase {
    private final FindInvoicesByClientIdPort findInvoicesByClientIdPort;

    @Override
    public List<Invoice> findInvoicesForClient(Client client) {
        return findInvoicesByClientIdPort.findInvoicesByClientId(client.uuid());
    }
}
