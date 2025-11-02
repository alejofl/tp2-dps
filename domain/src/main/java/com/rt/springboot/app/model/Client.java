package com.rt.springboot.app.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Client {
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private LocalDate createdAt;
    private List<Invoice> invoices;
}
