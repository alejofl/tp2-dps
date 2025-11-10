package com.rt.springboot.app.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Invoice(
    UUID uuid,
    String description,
    String observation,
    LocalDate createdAt,
    Client client,
    List<InvoiceItem> items
) {}
