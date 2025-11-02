package com.rt.springboot.app.port.driving.invoice;

import com.rt.springboot.app.model.Invoice;

public interface FindInvoiceUseCase {
    Invoice findById(Long id);
}

