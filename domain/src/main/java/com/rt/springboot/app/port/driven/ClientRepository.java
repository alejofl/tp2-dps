package com.rt.springboot.app.port.driven;

import com.rt.springboot.app.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(UUID id);

    void delete(UUID id);
}
