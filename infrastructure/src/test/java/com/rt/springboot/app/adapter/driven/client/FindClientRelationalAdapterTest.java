package com.rt.springboot.app.adapter.driven.client;
import com.rt.springboot.app.model.Client;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindClientRelationalAdapterTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private FindClientRelationalAdapter adapter;

    @Test
    public void findClientById() {
        UUID uuid = UUID.randomUUID();

        ClientRelationalEntity c = new ClientRelationalEntity();
        c.setUuid(uuid);
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setEmail("john@example.com");
        c.setPhoto("photo1.png");
        c.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));

        when(repository.findByUuid(uuid)).thenReturn(c);
        Client result = adapter.findById(uuid);

        assertThat(result.uuid()).isEqualTo(c.getUuid());
        assertThat(result.firstName()).isEqualTo(c.getFirstName());
        assertThat(result.lastName()).isEqualTo(c.getLastName());
        assertThat(result.email()).isEqualTo(c.getEmail());
        assertThat(result.photo()).isEqualTo(c.getPhoto());

    }

}
