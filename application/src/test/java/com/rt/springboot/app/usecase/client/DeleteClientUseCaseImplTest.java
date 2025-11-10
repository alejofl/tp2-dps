package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.port.driven.client.DeleteClientPort;
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
public class DeleteClientUseCaseImplTest {

    @Mock
    private DeleteClientPort deleteClientPort;

    @InjectMocks
    private DeleteClientUseCaseImpl deleteClientUseCase;


    @Test
    @DisplayName("Should delete client successfully")
    void shouldDeleteClientSuccessfully() {
        UUID clientId = UUID.randomUUID();

        // Given
        doNothing().when(deleteClientPort).delete(clientId);

        // When & Then
        assertDoesNotThrow(() -> deleteClientUseCase.delete(clientId));

        verify(deleteClientPort, times(1)).delete(clientId);
        verifyNoMoreInteractions(deleteClientPort);
    }

}
