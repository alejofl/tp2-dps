package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.port.driven.client.DeleteClientPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DrivenAdapter
@RequiredArgsConstructor
public class DeleteClientRelationalAdapter implements DeleteClientPort {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void delete(UUID id) {
        this.clientRepository.deleteByUuid(id);
    }
}

