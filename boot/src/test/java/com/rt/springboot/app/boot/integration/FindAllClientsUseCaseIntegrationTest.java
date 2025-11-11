package com.rt.springboot.app.boot.integration;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driving.client.CreateClientUseCase;
import com.rt.springboot.app.port.driving.client.FindAllClientsUseCase;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FindAllClientsUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateClientUseCase createClientUseCase;

    @Autowired
    private FindAllClientsUseCase findAllClientsUseCase;

    @Test
    @DisplayName("Should return all clients when clients exist")
    @WithMockUser(roles = "USER")
    void shouldReturnAllClientsWhenClientsExist() throws Exception {
        // Given
        Client client1 = createClientUseCase.create("Alice", "Smith", "alice@example.com", LocalDate.now(), "photo1.jpg");
        Client client2 = createClientUseCase.create("Bob", "Johnson", "bob@example.com", LocalDate.now(), "photo2.jpg");
        Client client3 = createClientUseCase.create("Charlie", "Brown", "charlie@example.com", LocalDate.now(), "photo3.jpg");

        // When, Then
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("clients"));

        // Verify
        List<Client> allClients = findAllClientsUseCase.findAll();
        assertThat(allClients).isNotNull();
        assertThat(allClients.size()).isGreaterThanOrEqualTo(3);
        assertThat(allClients).extracting(Client::uuid)
                .contains(client1.uuid(), client2.uuid(), client3.uuid());
        assertThat(allClients).extracting(Client::email)
                .contains("alice@example.com", "bob@example.com", "charlie@example.com");
    }

    @Test
    @DisplayName("Should return empty list when no clients exist")
    @WithMockUser(roles = "USER")
    void shouldReturnEmptyListWhenNoClientsExist() throws Exception {
        // When, Then
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("clients"));

        // Verify
        List<Client> allClients = findAllClientsUseCase.findAll();
        assertThat(allClients).isNotNull();
        assertThat(allClients).isEmpty();
    }

    @Test
    @DisplayName("Should return clients with all fields populated")
    @WithMockUser(roles = "USER")
    void shouldReturnClientsWithAllFieldsPopulated() throws Exception {
        // Given
        Client createdClient = createClientUseCase.create(
                "John",
                "Doe",
                "john.doe@example.com",
                LocalDate.of(2024, 1, 15),
                "photo.jpg"
        );

        // When, Then
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));

        // Verify
        List<Client> allClients = findAllClientsUseCase.findAll();
        assertThat(allClients).isNotEmpty();
        Client foundClient = allClients.stream()
                .filter(c -> c.uuid().equals(createdClient.uuid()))
                .findFirst()
                .orElse(null);

        assertThat(foundClient).isNotNull();
        assertThat(foundClient.firstName()).isEqualTo("John");
        assertThat(foundClient.lastName()).isEqualTo("Doe");
        assertThat(foundClient.email()).isEqualTo("john.doe@example.com");
        assertThat(foundClient.createdAt()).isEqualTo(LocalDate.of(2024, 1, 15));
        assertThat(foundClient.photo()).isEqualTo("photo.jpg");
    }
}

