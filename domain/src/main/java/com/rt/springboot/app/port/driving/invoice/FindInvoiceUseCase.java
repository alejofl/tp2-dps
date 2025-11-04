package com.rt.springboot.app.port.driving.invoice;

import com.rt.springboot.app.model.Invoice;

import java.util.UUID;

public interface FindInvoiceUseCase {
    Invoice findById(UUID id);
}

