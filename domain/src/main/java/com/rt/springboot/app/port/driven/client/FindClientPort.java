package com.rt.springboot.app.port.driven.client;

import com.rt.springboot.app.model.Client;

import java.util.UUID;

public interface FindClientPort {

    Client findById(UUID id);
}

