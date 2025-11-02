package com.rt.springboot.app.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Client {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private LocalDate createdAt;
}
