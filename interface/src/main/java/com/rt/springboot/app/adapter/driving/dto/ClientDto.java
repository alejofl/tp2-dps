package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ClientDto {
    private String id;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String createdAt;
}
