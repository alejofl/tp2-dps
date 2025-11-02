package com.rt.springboot.app.port.driving.client;

import com.rt.springboot.app.model.Client;

public interface FindClientUseCase {
    Client findOne(Long id);
}
