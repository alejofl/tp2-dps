package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.InvoiceItem;
import com.rt.springboot.app.port.driven.invoice.CreateInvoiceItemPort;
import com.rt.springboot.app.port.driving.invoice.CreateInvoiceItemUseCase;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import com.rt.springboot.app.port.driving.product.FindProductUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CreateInvoiceItemUseCaseImpl implements CreateInvoiceItemUseCase {

    private final FindProductUseCase findProductUseCase;
    private final FindInvoiceUseCase findInvoiceUseCase;
    private final CreateInvoiceItemPort createInvoiceItemPort;

    @Override
    public InvoiceItem create(UUID invoiceId, UUID productId, Integer amount) {
        final var product = this.findProductUseCase.findById(productId);
        final var invoice = this.findInvoiceUseCase.findById(invoiceId);
        return this.createInvoiceItemPort.create(invoice, product, amount);
    }
}
