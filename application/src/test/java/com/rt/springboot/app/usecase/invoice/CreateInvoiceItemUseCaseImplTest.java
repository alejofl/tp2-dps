package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.InvoiceItem;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.invoice.CreateInvoiceItemPort;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import com.rt.springboot.app.port.driving.product.FindProductUseCase;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateInvoiceItemUseCaseImplTest {

    @Mock
    private CreateInvoiceItemPort createInvoiceItemPort;

    @Mock
    private FindProductUseCase findProductUseCase;

    @Mock
    private FindInvoiceUseCase findInvoiceUseCase;

    @InjectMocks
    private CreateInvoiceItemUseCaseImpl createInvoiceItemUseCase;

    private UUID invoiceId;
    private UUID productId;
    private Integer amount;
    private Invoice invoice;
    private Product product;

    @BeforeEach
    void setUp() {
        this.invoiceId = UUID.randomUUID();
        this.productId = UUID.randomUUID();
        this.amount = 5;

        Client client = new Client(
                UUID.randomUUID(),
                "John",
                "Doe",
                "john.doe@example.com",
                "photo.jpg",
                LocalDate.now()
        );

        this.invoice = new Invoice(
                invoiceId,
                "Invoice description",
                "Invoice observation",
                LocalDate.now(),
                client,
                new ArrayList<>()
        );

        this.product = new Product(
                productId,
                "Product name",
                BigDecimal.valueOf(100.00),
                LocalDate.now()
        );
    }

    @Test
    @DisplayName("Should create invoice item successfully with valid data")
    void shouldCreateInvoiceItemSuccessfully() {
        // Given
        InvoiceItem expectedInvoiceItem = new InvoiceItem(amount, product);

        when(findProductUseCase.findById(productId)).thenReturn(product);
        when(findInvoiceUseCase.findById(invoiceId)).thenReturn(invoice);
        when(createInvoiceItemPort.create(
                eq(invoice),
                eq(product),
                eq(amount)
        )).thenReturn(expectedInvoiceItem);

        // When
        InvoiceItem result = createInvoiceItemUseCase.create(invoiceId, productId, amount);

        // Then
        assertNotNull(result);
        assertEquals(expectedInvoiceItem.amount(), result.amount());
        assertEquals(product, result.product());
    }

}

