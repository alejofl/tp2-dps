package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.FindClientPort;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FindClientUseCaseImpl implements FindClientUseCase {

    private final FindClientPort findClientPort;

    @Override
    public Client findById(UUID id) {
        return this.findClientPort.findById(id);
    }
}
