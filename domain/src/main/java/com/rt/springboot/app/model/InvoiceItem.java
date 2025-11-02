package com.rt.springboot.app.model;

import lombok.Data;

@Data
public class InvoiceItem {
    private int amount;
    private Product product;
}
