package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindInvoiceUseCaseImplTest {

    @Mock
    private FindInvoicePort findInvoicePort;

    @InjectMocks
    private FindInvoiceUseCaseImpl findInvoiceUseCase;

    private UUID invoiceId;
    private UUID clientId;
    private String description;
    private String observation;
    private LocalDate createdDate;
    private Client client;

    @BeforeEach
    void setUp() {
        this.invoiceId = UUID.randomUUID();
        this.clientId = UUID.randomUUID();
        this.description = "Invoice description";
        this.observation = "Invoice observation";
        this.createdDate = LocalDate.now();
        this.client = new Client(
                clientId,
                "John",
                "Doe",
                "john.doe@example.com",
                "photo.jpg",
                LocalDate.now()
        );
    }

    @Test
    @DisplayName("Should find invoice by id when invoice exists")
    void shouldFindInvoiceByIdWhenInvoiceExists() {
        // Given
        Invoice expectedInvoice = new Invoice(
                this.invoiceId,
                this.description,
                this.observation,
                this.createdDate,
                this.client,
                new ArrayList<>()
        );

        when(findInvoicePort.findById(invoiceId)).thenReturn(expectedInvoice);

        // When
        Invoice result = findInvoiceUseCase.findById(invoiceId);

        // Then
        assertNotNull(result);
        assertEquals(expectedInvoice.uuid(), result.uuid());
        assertEquals(description, result.description());
        assertEquals(observation, result.observation());
        assertEquals(createdDate, result.createdAt());
        assertEquals(client, result.client());
    }

}

