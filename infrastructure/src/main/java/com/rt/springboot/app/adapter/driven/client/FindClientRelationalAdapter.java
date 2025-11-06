package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.FindClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Adapter
@RequiredArgsConstructor
public class FindClientRelationalAdapter implements FindClientPort {

    private final ClientRepository clientRepository;

    @Override
    public Client findById(UUID id) {
        final var result = this.clientRepository.findByUuid(id);
        return ClientMapper.INSTANCE.toDomain(result);
    }
}

