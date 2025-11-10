package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.CreateClientPort;
import com.rt.springboot.app.port.driving.client.CreateClientUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CreateClientUseCaseImpl implements CreateClientUseCase {

    private final CreateClientPort createClientPort;

    @Override
    public Client create(String firstName, String lastName, String email, LocalDate createDate, String photo) {
        return createClientPort.create(UUID.randomUUID(), firstName, lastName, email, createDate, photo);
    }
}
