package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class InvoiceDto {
    private String id;
    private UUID uuid;
    private String description;
    private String observation;
    private String createdAt;
    private ClientDto client;
    private List<InvoiceItemDto> items;
    private String total;
}
