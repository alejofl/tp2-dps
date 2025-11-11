package com.rt.springboot.app.boot.integration;

import com.rt.springboot.app.adapter.driven.client.ClientRepository;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CreateClientUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindClientUseCase findClientUseCase;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Should create and persist client successfully")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateAndPersistClientSuccessfully() throws Exception {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate createDate = LocalDate.now();
        MockMultipartFile photo = new MockMultipartFile(
                "file",
                "photo.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "photo content".getBytes()
        );

        // When
        mockMvc.perform(multipart("/form")
                        .file(photo)
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("createdAt", createDate.toString())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));

        // Then
        Client foundClient = clientRepository.findAll().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .map(entity -> findClientUseCase.findById(entity.getUuid()))
                .orElse(null);

        assertThat(foundClient).isNotNull();
        assertThat(foundClient.firstName()).isEqualTo(firstName);
        assertThat(foundClient.lastName()).isEqualTo(lastName);
        assertThat(foundClient.email()).isEqualTo(email);
        assertThat(foundClient.createdAt()).isEqualTo(createDate);
    }

    @Test
    @DisplayName("Should create multiple clients with different data")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateMultipleClientsWithDifferentData() throws Exception {
        // Given
        MockMultipartFile photo1 = new MockMultipartFile("file", "photo1.jpg", MediaType.IMAGE_JPEG_VALUE, "content1".getBytes());
        MockMultipartFile photo2 = new MockMultipartFile("file", "photo2.jpg", MediaType.IMAGE_JPEG_VALUE, "content2".getBytes());

        // When
        mockMvc.perform(multipart("/form")
                        .file(photo1)
                        .param("firstName", "Alice")
                        .param("lastName", "Smith")
                        .param("email", "alice@example.com")
                        .param("createdAt", LocalDate.now().toString())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));

        mockMvc.perform(multipart("/form")
                        .file(photo2)
                        .param("firstName", "Bob")
                        .param("lastName", "Johnson")
                        .param("email", "bob@example.com")
                        .param("createdAt", LocalDate.now().toString())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));

        // Then
        Client foundClient1 = clientRepository.findAll().stream()
                .filter(c -> c.getEmail().equals("alice@example.com"))
                .findFirst()
                .map(entity -> findClientUseCase.findById(entity.getUuid()))
                .orElse(null);

        Client foundClient2 = clientRepository.findAll().stream()
                .filter(c -> c.getEmail().equals("bob@example.com"))
                .findFirst()
                .map(entity -> findClientUseCase.findById(entity.getUuid()))
                .orElse(null);

        assertThat(foundClient1).isNotNull();
        assertThat(foundClient2).isNotNull();
        assertThat(foundClient1.firstName()).isEqualTo("Alice");
        assertThat(foundClient2.firstName()).isEqualTo("Bob");
        assertThat(foundClient1.uuid()).isNotEqualTo(foundClient2.uuid());
        assertThat(foundClient1.email()).isNotEqualTo(foundClient2.email());
    }
}

