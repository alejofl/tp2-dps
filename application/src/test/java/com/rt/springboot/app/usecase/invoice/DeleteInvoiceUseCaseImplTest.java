package com.rt.springboot.app.usecase.invoice;

import com.rt.springboot.app.port.driven.invoice.DeleteInvoicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteInvoiceUseCaseImplTest {

    @Mock
    private DeleteInvoicePort deleteInvoicePort;

    @InjectMocks
    private DeleteInvoiceUseCaseImpl deleteInvoiceUseCase;

    @Test
    @DisplayName("Should delete invoice successfully")
    void shouldDeleteInvoiceSuccessfully() {
        UUID invoiceId = UUID.randomUUID();

        // Given
        doNothing().when(deleteInvoicePort).delete(invoiceId);

        // When & Then
        assertDoesNotThrow(() -> deleteInvoiceUseCase.delete(invoiceId));

        verify(deleteInvoicePort, times(1)).delete(invoiceId);
        verifyNoMoreInteractions(deleteInvoicePort);
    }

}

