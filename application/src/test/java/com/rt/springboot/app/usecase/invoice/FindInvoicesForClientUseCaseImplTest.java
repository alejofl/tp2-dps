package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.invoice.FindInvoicesByClientIdPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindInvoicesForClientUseCaseImplTest {

    @Mock
    private FindInvoicesByClientIdPort findInvoicesByClientIdPort;

    @InjectMocks
    private FindInvoicesForClientUseCaseImpl findInvoicesForClientUseCase;

    private Client client;

    @BeforeEach
    void setUp() {
        this.client = new Client(
                UUID.randomUUID(),
                "John",
                "Doe",
                "john.doe@example.com",
                "photo.jpg",
                LocalDate.now()
        );
    }

    @Test
    @DisplayName("Should return list of invoices when invoices exist for client")
    void shouldReturnListOfInvoicesWhenInvoicesExistForClient() {
        // Given
        List<Invoice> expectedInvoices = createMockInvoices(3);
        when(findInvoicesByClientIdPort.findInvoicesByClientId(client.uuid())).thenReturn(expectedInvoices);

        // When
        List<Invoice> result = findInvoicesForClientUseCase.findInvoicesForClient(client);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedInvoices, result);
    }

    @Test
    @DisplayName("Should return empty list when no invoices exist for client")
    void shouldReturnEmptyListWhenNoInvoicesExistForClient() {
        // Given
        when(findInvoicesByClientIdPort.findInvoicesByClientId(client.uuid())).thenReturn(new ArrayList<>());

        // When
        List<Invoice> result = findInvoicesForClientUseCase.findInvoicesForClient(client);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private List<Invoice> createMockInvoices(int count) {
        List<Invoice> invoices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Invoice invoice = new Invoice(
                    UUID.randomUUID(),
                    "Description " + i,
                    "Observation " + i,
                    LocalDate.now(),
                    client,
                    new ArrayList<>()
            );
            invoices.add(invoice);
        }
        return invoices;
    }

}

