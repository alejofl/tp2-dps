package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.Pair;
import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.CreateInvoiceItemPort;
import com.rt.springboot.app.port.driven.invoice.CreateInvoicePort;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import com.rt.springboot.app.port.driving.invoice.CreateInvoiceItemUseCase;
import com.rt.springboot.app.port.driving.invoice.CreateInvoiceUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CreateInvoiceUseCaseImpl implements CreateInvoiceUseCase {

    private final CreateInvoicePort createInvoicePort;
    private final CreateInvoiceItemUseCase createInvoiceItemUseCase;
    private final FindClientUseCase findClientUseCase;

    @Override
    public Invoice create(String description, String observation, UUID clientId, List<Pair<UUID, Integer>> items) {
        final var client = findClientUseCase.findById(clientId);
        Invoice invoice = createInvoicePort.create(UUID.randomUUID(), description, observation, LocalDate.now(), client);
        items.forEach(item -> createInvoiceItemUseCase.create(invoice.getUuid(), item.getFirst(), item.getSecond()));
        return invoice;
    }
}
