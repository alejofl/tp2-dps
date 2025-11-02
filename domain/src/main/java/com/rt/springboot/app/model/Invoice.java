package com.rt.springboot.app.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Invoice {
    private String description;
    private String observation;
    private LocalDate createdAt;
    private Client client;
    private List<InvoiceItem> items;
}
