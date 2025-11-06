package com.rt.springboot.app.port.driven.client;

import com.rt.springboot.app.model.Client;

import java.util.List;

public interface FindAllClientsPort {

    List<Client> findAll();
}
