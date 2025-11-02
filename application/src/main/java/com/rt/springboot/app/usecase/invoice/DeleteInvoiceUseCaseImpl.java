package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.port.driven.InvoiceRepository;
import com.rt.springboot.app.port.driving.invoice.DeleteInvoiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInvoiceUseCaseImpl implements DeleteInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;

    @Override
    public void delete(Long id) {
        this.invoiceRepository.delete(id);
    }
}
