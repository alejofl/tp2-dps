package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.FindAllClientsPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DrivenAdapter
@RequiredArgsConstructor
public class FindAllClientsRelationalAdapter implements FindAllClientsPort {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public List<Client> findAll() {
        return this.clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toDomain)
                .toList();
    }
}

