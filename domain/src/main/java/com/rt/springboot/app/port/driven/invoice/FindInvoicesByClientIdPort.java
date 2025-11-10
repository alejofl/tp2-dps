package com.rt.springboot.app.port.driven.invoice;

import com.rt.springboot.app.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface FindInvoicesByClientIdPort {
    List<Invoice> findInvoicesByClientId(UUID id);
}
