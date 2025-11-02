package com.rt.springboot.app.port.driving.invoice;

import com.rt.springboot.app.model.Invoice;

public interface SaveInvoiceUseCase {
    void save(Invoice invoice);
}

