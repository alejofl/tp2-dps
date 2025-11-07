package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class CreateInvoiceDto {
    @NotEmpty
    private String description;
    private String observation;
    private ClientDto client;
}
