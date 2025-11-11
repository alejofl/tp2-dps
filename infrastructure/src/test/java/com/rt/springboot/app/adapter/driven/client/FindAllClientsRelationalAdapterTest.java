package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.adapter.driven.attachment.AttachmentRepository;
import com.rt.springboot.app.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllClientsRelationalAdapterTest {

    @Mock
    ClientRepository repository;

    @InjectMocks
    FindAllClientsRelationalAdapter adapter;

    @Test
    public void findAllClientsRelationalAdapterTest(){
        ClientRelationalEntity c1 = new ClientRelationalEntity();
        c1.setUuid(UUID.randomUUID());
        c1.setFirstName("John");
        c1.setLastName("Doe");
        c1.setEmail("john@example.com");
        c1.setPhoto("photo1.png");
        c1.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));

        ClientRelationalEntity c2 = new ClientRelationalEntity();
        c2.setUuid(UUID.randomUUID());
        c2.setFirstName("Jane");
        c2.setLastName("Smith");
        c2.setEmail("jane@example.com");
        c2.setPhoto("photo2.png");
        c2.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 2)));

        when(repository.findAll()).thenReturn(List.of(c1, c2));

        List<Client> result = adapter.findAll();

        assertThat(result).hasSize(2);

        Client client1 = result.get(0);
        Client client2 = result.get(1);

        assertThat(client1.uuid()).isEqualTo(c1.getUuid());
        assertThat(client1.firstName()).isEqualTo(c1.getFirstName());
        assertThat(client1.lastName()).isEqualTo(c1.getLastName());
        assertThat(client1.email()).isEqualTo(c1.getEmail());
        assertThat(client1.photo()).isEqualTo(c1.getPhoto());

        assertThat(client2.uuid()).isEqualTo(c2.getUuid());
        assertThat(client2.firstName()).isEqualTo(c2.getFirstName());
        assertThat(client2.lastName()).isEqualTo(c2.getLastName());
        assertThat(client2.email()).isEqualTo(c2.getEmail());
        assertThat(client2.photo()).isEqualTo(c2.getPhoto());
    }
}
