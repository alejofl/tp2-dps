package com.rt.springboot.app.port.driving.client;

import com.rt.springboot.app.model.Client;

import java.util.List;

public interface FindAllClientsUseCase {
    List<Client> findAll();
}
