package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.UpdateClientPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateClientUseCaseImpTest {

    @Mock
    private UpdateClientPort updateClientPort;

    @InjectMocks
    private UpdateClientUseCaseImp updateClientUseCase;

    private UUID clientId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate createdDate;
    private String photo;

    @BeforeEach
    void setUp() {
        this.clientId = UUID.randomUUID();
        this.firstName = "John";
        this.lastName = "Doe";
        this.email = "john.doe@example.com";
        this.createdDate = LocalDate.now();
        this.photo = "photo.jpg";
    }

    @Test
    @DisplayName("Should update client successfully with valid data")
    void shouldUpdateClientSuccessfully() {
        // Given
        Client expectedClient = new Client(
                this.clientId,
                this.firstName,
                this.lastName,
                this.email,
                this.photo,
                this.createdDate
        );

        when(updateClientPort.update(
                eq(clientId),
                eq(firstName),
                eq(lastName),
                eq(email),
                eq(createdDate),
                eq(photo)
        )).thenReturn(expectedClient);

        // When
        Client result = updateClientUseCase.update(clientId, firstName, lastName, email, createdDate, photo);

        // Then
        assertNotNull(result);
        assertEquals(expectedClient.uuid(), result.uuid());
        assertEquals(firstName, result.firstName());
        assertEquals(lastName, result.lastName());
        assertEquals(email, result.email());
        assertEquals(photo, result.photo());
        assertEquals(createdDate, result.createdAt());
    }

}

