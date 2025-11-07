package com.rt.springboot.app.port.driving.invoice;

import com.rt.springboot.app.model.InvoiceItem;

import java.util.UUID;

public interface CreateInvoiceItemUseCase {
    InvoiceItem create(UUID invoiceId, UUID productId, Integer amount);
}
