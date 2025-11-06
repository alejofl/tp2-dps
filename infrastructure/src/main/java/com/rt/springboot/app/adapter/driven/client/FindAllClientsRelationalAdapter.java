package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.FindAllClientsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Adapter
@RequiredArgsConstructor
public class FindAllClientsRelationalAdapter implements FindAllClientsPort {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toDomain)
                .toList();
    }
}

