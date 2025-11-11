package com.rt.springboot.app.adapter.driven.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteClientRelationalAdapterTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private DeleteClientRelationalAdapter adapter;

    @Test
    void deleteClient() {
        UUID uuid = UUID.randomUUID();
        adapter.delete(uuid);
        verify(repository).deleteByUuid(uuid);
    }
}
