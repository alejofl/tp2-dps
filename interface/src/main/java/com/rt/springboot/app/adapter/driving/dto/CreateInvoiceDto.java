package com.rt.springboot.app.adapter.driving.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateInvoiceDto {
    @NotEmpty
    private String description;
    private String observation;
    private ClientDto client;
}
