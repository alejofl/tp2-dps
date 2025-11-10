package com.rt.springboot.app.port.driven.client;

import com.rt.springboot.app.model.Client;

import java.time.LocalDate;
import java.util.UUID;

public interface UpdateClientPort {

    Client update(UUID id, String firstName, String lastName, String email, LocalDate createDate, String photo);
}
