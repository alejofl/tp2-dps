package com.rt.springboot.app.port.driving.invoice;

import com.rt.springboot.app.Pair;
import com.rt.springboot.app.model.Invoice;

import java.util.List;

import java.util.UUID;

public interface CreateInvoiceUseCase {

    Invoice create(String description, String observation, UUID clientId, List<Pair<UUID, Integer>> items);
}
