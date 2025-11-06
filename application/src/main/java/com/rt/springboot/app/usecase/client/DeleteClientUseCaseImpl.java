package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.port.driven.client.DeleteClientPort;
import com.rt.springboot.app.port.driving.client.DeleteClientUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {

    private final DeleteClientPort deleteClientPort;

    @Override
    public void delete(UUID id) {
        this.deleteClientPort.delete(id);
    }
}
