package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.client.ClientRepository;
import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.CreateInvoicePort;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Adapter
@RequiredArgsConstructor
public class CreateInvoiceRelationalAdapter implements CreateInvoicePort {
    private final ClientRepository clientRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice create(UUID uuid, String description, String observation, LocalDate createdAt, Client client) {
        final var clientEntity = clientRepository.findByUuid(client.getUuid());
        final var invoiceEntity = new InvoiceRelationalEntity();
        invoiceEntity.setUuid(uuid);
        invoiceEntity.setDescription(description);
        invoiceEntity.setObservation(observation);
        invoiceEntity.setCreatedAt(Date.valueOf(createdAt));
        invoiceEntity.setClient(clientEntity);
        return InvoiceMapper.INSTANCE.toDomain(invoiceRepository.save(invoiceEntity));
    }
}
