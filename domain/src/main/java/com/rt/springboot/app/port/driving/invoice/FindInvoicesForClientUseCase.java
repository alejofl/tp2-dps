package com.rt.springboot.app.port.driving.invoice;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;

import java.util.List;

public interface FindInvoicesForClientUseCase {
    List<Invoice> findInvoicesForClient(Client client);
}
