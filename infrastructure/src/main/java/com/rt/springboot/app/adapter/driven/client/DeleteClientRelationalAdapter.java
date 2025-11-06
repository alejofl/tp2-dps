package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.port.driven.client.DeleteClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Adapter
@RequiredArgsConstructor
public class DeleteClientRelationalAdapter implements DeleteClientPort {

    private final ClientRepository clientRepository;

    @Override
    public void delete(UUID id) {
        this.clientRepository.deleteByUuid(id);
    }
}

