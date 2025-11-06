package com.rt.springboot.app.port.driven.invoice;

import com.rt.springboot.app.model.Invoice;

import java.util.UUID;

public interface FindInvoicePort {

    Invoice findById(UUID id);
}

