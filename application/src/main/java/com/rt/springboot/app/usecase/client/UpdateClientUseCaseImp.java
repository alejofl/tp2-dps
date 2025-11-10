package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.UpdateClientPort;
import com.rt.springboot.app.port.driving.client.UpdateClientUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateClientUseCaseImp implements UpdateClientUseCase {

    private final UpdateClientPort updateClientPort;

    @Override
    public Client update(UUID id, String firstName, String lastName, String email, LocalDate createDate, String photo) {
        return updateClientPort.update(id, firstName, lastName, email, createDate, photo);
    }
}
