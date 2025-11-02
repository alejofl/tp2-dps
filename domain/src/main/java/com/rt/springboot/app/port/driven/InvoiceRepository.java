package com.rt.springboot.app.port.driven;

import com.rt.springboot.app.model.Invoice;

public interface InvoiceRepository {

    Invoice findById(long id);

    void delete(long id);
}
