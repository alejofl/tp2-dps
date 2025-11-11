package com.rt.springboot.app.boot.integration;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driving.client.CreateClientUseCase;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class DeleteClientUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateClientUseCase createClientUseCase;

    @Autowired
    private FindClientUseCase findClientUseCase;

    @Test
    @DisplayName("Should delete client successfully")
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteClientSuccessfully() throws Exception {
        // Given
        Client createdClient = createClientUseCase.create(
                "John",
                "Doe",
                "john.doe@example.com",
                LocalDate.now(),
                "photo.jpg"
        );

        Client foundClient = findClientUseCase.findById(createdClient.uuid());
        assertThat(foundClient).isNotNull();

        // When
        mockMvc.perform(get("/delete/{id}", createdClient.uuid()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));

        // Then
        Client deletedClient = findClientUseCase.findById(createdClient.uuid());
        assertThat(deletedClient).isNull();
    }

    @Test
    @DisplayName("Should delete only the specified client")
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteOnlyTheSpecifiedClient() throws Exception {
        // Given
        Client client1 = createClientUseCase.create("Alice", "Smith", "alice@example.com", LocalDate.now(), "photo1.jpg");
        Client client2 = createClientUseCase.create("Bob", "Johnson", "bob@example.com", LocalDate.now(), "photo2.jpg");
        Client client3 = createClientUseCase.create("Charlie", "Brown", "charlie@example.com", LocalDate.now(), "photo3.jpg");

        // When
        mockMvc.perform(get("/delete/{id}", client2.uuid()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));

        // Then
        assertThat(findClientUseCase.findById(client1.uuid())).isNotNull();
        assertThat(findClientUseCase.findById(client2.uuid())).isNull();
        assertThat(findClientUseCase.findById(client3.uuid())).isNotNull();
    }

    @Test
    @DisplayName("Should handle deletion of non-existent client gracefully")
    @WithMockUser(roles = "ADMIN")
    void shouldHandleDeletionOfNonExistentClientGracefully() throws Exception {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When, Then
        mockMvc.perform(get("/delete/{id}", nonExistentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));
    }
}

