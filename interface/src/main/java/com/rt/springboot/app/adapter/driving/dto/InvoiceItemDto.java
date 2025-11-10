package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InvoiceItemDto {
    private int amount;
    private ProductDto product;
    private String subtotal;
}
