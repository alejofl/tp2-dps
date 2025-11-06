package com.rt.springboot.app.port.driven.invoice;

import java.util.UUID;

public interface DeleteInvoicePort {

    void delete(UUID id);
}

