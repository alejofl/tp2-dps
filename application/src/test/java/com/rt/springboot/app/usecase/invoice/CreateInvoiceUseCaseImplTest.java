package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.Pair;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.InvoiceItem;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.invoice.CreateInvoicePort;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import com.rt.springboot.app.port.driving.invoice.CreateInvoiceItemUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateInvoiceUseCaseImplTest {

    @Mock
    private CreateInvoicePort createInvoicePort;

    @Mock
    private CreateInvoiceItemUseCase createInvoiceItemUseCase;

    @Mock
    private FindClientUseCase findClientUseCase;

    @InjectMocks
    private CreateInvoiceUseCaseImpl createInvoiceUseCase;

    private UUID clientId;
    private String description;
    private String observation;
    private Client client;
    private List<Pair<UUID, Integer>> items;

    @BeforeEach
    void setUp() {
        this.clientId = UUID.randomUUID();
        this.description = "Invoice description";
        this.observation = "Invoice observation";
        this.client = new Client(
                clientId,
                "John",
                "Doe",
                "john.doe@example.com",
                "photo.jpg",
                LocalDate.now()
        );
        this.items = new ArrayList<>();
        this.items.add(new Pair<>(UUID.randomUUID(), 5));
        this.items.add(new Pair<>(UUID.randomUUID(), 10));
    }

    @Test
    @DisplayName("Should create invoice successfully with valid data")
    void shouldCreateInvoiceSuccessfully() {
        // Given
        Invoice expectedInvoice = new Invoice(
                UUID.randomUUID(),
                description,
                observation,
                LocalDate.now(),
                client,
                new ArrayList<>()
        );

        when(findClientUseCase.findById(clientId)).thenReturn(client);
        when(createInvoicePort.create(
                any(UUID.class),
                eq(description),
                eq(observation),
                any(LocalDate.class),
                eq(client)
        )).thenReturn(expectedInvoice);
        
        Product mockProduct = new Product(UUID.randomUUID(), "Product", BigDecimal.valueOf(100.00), LocalDate.now());
        InvoiceItem mockInvoiceItem = new InvoiceItem(5, mockProduct);
        when(createInvoiceItemUseCase.create(any(UUID.class), any(UUID.class), any(Integer.class)))
                .thenReturn(mockInvoiceItem);

        // When
        Invoice result = createInvoiceUseCase.create(description, observation, clientId, items);

        // Then
        assertNotNull(result);
        assertEquals(expectedInvoice.uuid(), result.uuid());
        assertEquals(description, result.description());
        assertEquals(observation, result.observation());
        assertEquals(client, result.client());
    }

}

