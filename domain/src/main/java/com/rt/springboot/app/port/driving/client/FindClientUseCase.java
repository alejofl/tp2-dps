package com.rt.springboot.app.port.driving.client;

import com.rt.springboot.app.model.Client;

import java.util.UUID;

public interface FindClientUseCase {
    Client findById(UUID id);
}
