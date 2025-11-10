package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.CreateClientPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@DrivenAdapter
@RequiredArgsConstructor
public class CreateClientRelationalAdapter implements CreateClientPort {

    private final ClientRepository clientRepository;

    @Override
    public Client create(String firstName, String lastName, String email, LocalDate createDate, String photo) {
        final var client  = new ClientRelationalEntity();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setCreatedAt(java.sql.Date.valueOf(createDate));
        client.setPhoto(photo);
        return ClientMapper.INSTANCE.toDomain(clientRepository.save(client));
    }
}
