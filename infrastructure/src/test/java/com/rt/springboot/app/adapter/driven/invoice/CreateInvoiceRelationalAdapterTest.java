package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.client.ClientRelationalEntity;
import com.rt.springboot.app.adapter.driven.client.ClientRepository;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.CreateInvoicePort;
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
public class CreateInvoiceRelationalAdapterTest {

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private CreateInvoiceRelationalAdapter adapter;

    @Test
    public void createInvoice() {
        UUID invoiceUuid = UUID.randomUUID();
        UUID clientUuid = UUID.randomUUID();

        String description = "Invoice description";
        String observation = "Some observation";
        LocalDate createdDate = LocalDate.of(2024, 1, 1);

        Client domainClient = new Client(
                clientUuid,
                "John",
                "Doe",
                "john@example.com",
                "photo.png",
                createdDate
        );

        ClientRelationalEntity clientEntity = new ClientRelationalEntity();
        clientEntity.setUuid(clientUuid);
        clientEntity.setFirstName("John");
        clientEntity.setLastName("Doe");
        clientEntity.setEmail("john@example.com");
        clientEntity.setPhoto("photo.png");
        clientEntity.setCreatedAt(Date.valueOf(createdDate));

        when(clientRepository.findByUuid(clientUuid)).thenReturn(clientEntity);

        InvoiceRelationalEntity savedEntity = new InvoiceRelationalEntity();
        savedEntity.setUuid(invoiceUuid);
        savedEntity.setDescription(description);
        savedEntity.setObservation(observation);
        savedEntity.setCreatedAt(Date.valueOf(createdDate));
        savedEntity.setClient(clientEntity);

        when(invoiceRepository.save(any(InvoiceRelationalEntity.class)))
                .thenReturn(savedEntity);

        Invoice result = adapter.create(
                invoiceUuid,
                description,
                observation,
                createdDate,
                domainClient
        );

        verify(clientRepository).findByUuid(clientUuid);
        verify(invoiceRepository).save(any(InvoiceRelationalEntity.class));

        assertThat(result.uuid()).isEqualTo(invoiceUuid);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.observation()).isEqualTo(observation);
        assertThat(result.createdAt()).isEqualTo(createdDate);

        assertThat(result.client()).isNotNull();
        assertThat(result.client().uuid()).isEqualTo(clientUuid);

    }
}
