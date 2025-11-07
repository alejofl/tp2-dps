package com.rt.springboot.app.port.driven.invoice;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.InvoiceItem;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CreateInvoicePort {

    Invoice create(UUID uuid, String description, String observation, LocalDate createdAt, Client client);
}
