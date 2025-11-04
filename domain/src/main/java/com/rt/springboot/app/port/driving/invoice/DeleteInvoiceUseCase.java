package com.rt.springboot.app.port.driving.invoice;

import java.util.UUID;

public interface DeleteInvoiceUseCase {
    void delete(UUID id);
}

