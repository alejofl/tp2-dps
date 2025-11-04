package com.rt.springboot.app.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Invoice {
    private UUID uuid;
    private String description;
    private String observation;
    private LocalDate createdAt;
    private Client client;
    private List<InvoiceItem> items;
}
