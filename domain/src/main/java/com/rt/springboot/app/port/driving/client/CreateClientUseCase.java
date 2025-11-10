package com.rt.springboot.app.port.driving.client;

import com.rt.springboot.app.model.Client;

import java.time.LocalDate;

public interface CreateClientUseCase {
    Client create(String firstName, String lastName, String email, LocalDate createDate, String photo);
}
