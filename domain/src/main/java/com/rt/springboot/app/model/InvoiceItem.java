package com.rt.springboot.app.model;

public record InvoiceItem(
    int amount,
    Product product
) {}
