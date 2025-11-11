package com.rt.springboot.app.adapter.driven.invoice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteInvoiceRelationalAdapterTest {
    @Mock
    private InvoiceRepository repository;

    @InjectMocks
    private DeleteInvoiceRelationalAdapter adapter;

    @Test
    void deleteInvoice() {
        UUID uuid = UUID.randomUUID();
        adapter.delete(uuid);
        verify(repository).deleteByUuid(uuid);
    }
}
