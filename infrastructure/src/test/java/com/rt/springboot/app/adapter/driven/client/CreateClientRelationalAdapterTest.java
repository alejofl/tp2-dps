package com.rt.springboot.app.adapter.driven.client;
import com.rt.springboot.app.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateClientRelationalAdapterTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private CreateClientRelationalAdapter adapter;

    @Test
    void createClient() {
        UUID uuid = UUID.randomUUID();
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate date = LocalDate.of(2024, 1, 1);
        String photo = "image.png";

        ClientRelationalEntity entity = new ClientRelationalEntity();
        entity.setUuid(uuid);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setEmail(email);
        entity.setCreatedAt(Date.valueOf(date));
        entity.setPhoto(photo);

        when(repository.save(any(ClientRelationalEntity.class))).thenReturn(entity);

        Client result = adapter.create(uuid, firstName, lastName, email, date, photo);

        verify(repository).save(any(ClientRelationalEntity.class));

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(email);
        assertThat(result.lastName()).isEqualTo(lastName);
        assertThat(result.firstName()).isEqualTo(firstName);
        assertThat(result.photo()).isEqualTo(photo);
        assertThat(result.createdAt()).isEqualTo(date);
        assertThat(result.photo()).isEqualTo(photo);
    }
}
