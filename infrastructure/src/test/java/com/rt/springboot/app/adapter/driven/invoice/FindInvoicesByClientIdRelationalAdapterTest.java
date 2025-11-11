package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.client.ClientRelationalEntity;
import com.rt.springboot.app.model.Invoice;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindInvoicesByClientIdRelationalAdapterTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private FindInvoicesByClientIdRelationalAdapter adapter;

    @Test
    void shouldReturnInvoicesByClientId() {
        UUID clientId = UUID.randomUUID();

        ClientRelationalEntity c = new ClientRelationalEntity();
        c.setUuid(clientId);
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setEmail("john@example.com");
        c.setPhoto("photo1.png");
        c.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));

        InvoiceRelationalEntity inv1 = new InvoiceRelationalEntity();
        inv1.setUuid(UUID.randomUUID());
        inv1.setDescription("First Invoice");
        inv1.setObservation("OK");
        inv1.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));
        inv1.setClient(c);

        InvoiceRelationalEntity inv2 = new InvoiceRelationalEntity();
        inv2.setUuid(UUID.randomUUID());
        inv2.setDescription("Second Invoice");
        inv2.setObservation("Pending");
        inv2.setCreatedAt(Date.valueOf(LocalDate.of(2024, 2, 1)));
        inv2.setClient(c);

        when(invoiceRepository.findByClientUuid(clientId)).thenReturn(List.of(inv1, inv2));

        List<Invoice> result = adapter.findInvoicesByClientId(clientId);

        verify(invoiceRepository).findByClientUuid(clientId);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).description()).isEqualTo("First Invoice");
        assertThat(result.get(0).observation()).isEqualTo("OK");
        assertThat(result.get(0).createdAt()).isEqualTo(LocalDate.of(2024, 1, 1));

        assertThat(result.get(1).description()).isEqualTo("Second Invoice");
        assertThat(result.get(1).observation()).isEqualTo("Pending");
        assertThat(result.get(1).createdAt()).isEqualTo(LocalDate.of(2024, 2, 1));
    }
}
