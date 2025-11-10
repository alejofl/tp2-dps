package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.FindAllClientsPort;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllClientsUseCaseImplTest {

    @Mock
    private FindAllClientsPort findAllClientsPort;

    @InjectMocks
    private FindAllClientsUseCaseImpl findAllClientsUseCase;

    @Test
    @DisplayName("Should return list of clients when clients exist")
    void shouldReturnListOfClientsWhenClientsExist() {
        // Given
        List<Client> expectedClients = createMockClients(3);
        when(findAllClientsPort.findAll()).thenReturn(expectedClients);

        // When
        List<Client> result = findAllClientsUseCase.findAll();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedClients, result);
    }

    @Test
    @DisplayName("Should return empty list when no clients exist")
    void shouldReturnEmptyListWhenNoClientsExist() {
        // Given
        when(findAllClientsPort.findAll()).thenReturn(new ArrayList<>());

        // When
        List<Client> result = findAllClientsUseCase.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    private List<Client> createMockClients(int count) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Client client = new Client(
                    UUID.randomUUID(),
                    "FirstName" + i,
                    "LastName" + i,
                    "email" + i + "@example.com",
                    "photo" + i + ".jpg",
                    LocalDate.now()
            );

            clients.add(client);
        }

        return clients;
    }

}
