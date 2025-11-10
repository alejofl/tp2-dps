package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.CreateClientPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@DrivenAdapter
@RequiredArgsConstructor
public class CreateClientRelationalAdapter implements CreateClientPort {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Client create(UUID uuid, String firstName, String lastName, String email, LocalDate createDate, String photo) {
        final var client  = new ClientRelationalEntity();
        client.setUuid(uuid);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setCreatedAt(java.sql.Date.valueOf(createDate));
        client.setPhoto(photo);
        return ClientMapper.INSTANCE.toDomain(clientRepository.save(client));
    }
}
