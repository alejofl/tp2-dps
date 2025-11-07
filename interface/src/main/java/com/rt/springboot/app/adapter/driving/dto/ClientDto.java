package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ClientDto {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private LocalDate createdAt;
}
