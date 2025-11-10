package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.CreateClientPort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateClientUseCaseImplTest {

    @Mock
    private CreateClientPort createClientPort;

    @InjectMocks
    private CreateClientUseCaseImpl createClientUseCase;

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
    @DisplayName("Should create client successfully with valid data")
    void shouldCreateClientSuccessfully() {
        //Given
        Client expectedClient = new Client(
                this.clientId,
                this.firstName,
                this.lastName,
                this.email,
                this.photo,
                this.createdDate
        );

        when(createClientPort.create(
                any(UUID.class),
                eq(firstName),
                eq(lastName),
                eq(email),
                eq(createdDate),
                eq(photo)
        )).thenReturn(expectedClient);

        //When
        Client result = createClientUseCase.create(firstName, lastName, email, createdDate, photo);

        //Then
        assertNotNull(result);
        assertEquals(expectedClient.uuid(), result.uuid());
        assertEquals(firstName, result.firstName());
        assertEquals(lastName, result.lastName());
        assertEquals(email, result.email());
        assertEquals(photo, result.photo());
        assertEquals(createdDate, result.createdAt());

        // verify(createClientPort, times(1)).create(firstName, lastName, email, createdDate, photo);
        // verifyNoMoreInteractions(createClientPort);
    }

}
