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
class FindClientUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateClientUseCase createClientUseCase;

    @Autowired
    private FindClientUseCase findClientUseCase;

    @Test
    @DisplayName("Should find client by id when client exists")
    @WithMockUser(roles = "USER")
    void shouldFindClientByIdWhenClientExists() throws Exception {
        // Given
        Client createdClient = createClientUseCase.create(
                "Alice",
                "Smith",
                "alice.smith@example.com",
                LocalDate.now(),
                "photo.jpg"
        );

        // When, Then
        mockMvc.perform(get("/view/{id}", createdClient.uuid()))
                .andExpect(status().isOk())
                .andExpect(view().name("view"))
                .andExpect(model().attributeExists("client"));

        // Verify
        Client foundClient = findClientUseCase.findById(createdClient.uuid());
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.uuid()).isEqualTo(createdClient.uuid());
        assertThat(foundClient.firstName()).isEqualTo("Alice");
        assertThat(foundClient.lastName()).isEqualTo("Smith");
        assertThat(foundClient.email()).isEqualTo("alice.smith@example.com");
    }

    @Test
    @DisplayName("Should return null when client does not exist")
    @WithMockUser(roles = "USER")
    void shouldReturnNullWhenClientDoesNotExist() throws Exception {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When, Then
        mockMvc.perform(get("/view/{id}", nonExistentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));

        // Verify
        Client foundClient = findClientUseCase.findById(nonExistentId);
        assertThat(foundClient).isNull();
    }

    @Test
    @DisplayName("Should find multiple clients by their ids")
    @WithMockUser(roles = "USER")
    void shouldFindMultipleClientsByTheirIds() throws Exception {
        // Given
        Client client1 = createClientUseCase.create(
                "Bob",
                "Johnson",
                "bob@example.com",
                LocalDate.now(),
                "photo1.jpg"
        );
        Client client2 = createClientUseCase.create(
                "Charlie",
                "Brown",
                "charlie@example.com",
                LocalDate.now(),
                "photo2.jpg"
        );

        // When, Then
        mockMvc.perform(get("/view/{id}", client1.uuid()))
                .andExpect(status().isOk())
                .andExpect(view().name("view"));

        // When, Then
        mockMvc.perform(get("/view/{id}", client2.uuid()))
                .andExpect(status().isOk())
                .andExpect(view().name("view"));

        // Verify
        Client foundClient1 = findClientUseCase.findById(client1.uuid());
        Client foundClient2 = findClientUseCase.findById(client2.uuid());

        assertThat(foundClient1).isNotNull();
        assertThat(foundClient2).isNotNull();
        assertThat(foundClient1.uuid()).isNotEqualTo(foundClient2.uuid());
        assertThat(foundClient1.firstName()).isEqualTo("Bob");
        assertThat(foundClient2.firstName()).isEqualTo("Charlie");
    }
}

