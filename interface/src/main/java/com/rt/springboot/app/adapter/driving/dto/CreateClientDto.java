package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateClientDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate createdAt;
    private String photo;
}
