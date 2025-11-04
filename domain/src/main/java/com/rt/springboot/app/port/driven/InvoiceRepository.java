package com.rt.springboot.app.port.driven;

import com.rt.springboot.app.model.Invoice;

import java.util.UUID;

public interface InvoiceRepository {

    Invoice findById(UUID id);

    void delete(UUID id);
}
