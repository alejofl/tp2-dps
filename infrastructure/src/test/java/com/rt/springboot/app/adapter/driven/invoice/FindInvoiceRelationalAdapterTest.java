package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.client.ClientRelationalEntity;
import com.rt.springboot.app.model.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindInvoiceRelationalAdapterTest {

    @Mock
    private InvoiceRepository repository;

    @InjectMocks
    private FindInvoiceRelationalAdapter adapter;

    @Test
    public void findInvoice() {
        UUID id = UUID.randomUUID();
        InvoiceRelationalEntity entity = new InvoiceRelationalEntity();
        entity.setUuid(id);
        entity.setDescription("Invoice 1");
        entity.setObservation("OK");
        entity.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));

        ClientRelationalEntity c = new ClientRelationalEntity();
        c.setUuid(UUID.randomUUID());
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setEmail("john@example.com");
        c.setPhoto("photo1.png");
        c.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));
        entity.setClient(c);

        when(repository.findByUuid(id)).thenReturn(entity);

        Invoice result = adapter.findById(id);

        verify(repository).findByUuid(id);

        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(id);
        assertThat(result.description()).isEqualTo("Invoice 1");
        assertThat(result.observation()).isEqualTo("OK");
        assertThat(result.createdAt()).isEqualTo(LocalDate.of(2024, 1, 1));
    }
}
