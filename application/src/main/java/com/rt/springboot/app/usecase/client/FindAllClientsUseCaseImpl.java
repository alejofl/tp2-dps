package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.client.FindAllClientsPort;
import com.rt.springboot.app.port.driving.client.FindAllClientsUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class FindAllClientsUseCaseImpl implements FindAllClientsUseCase {

    private final FindAllClientsPort findAllClientsPort;

    @Override
    public List<Client> findAll() {
        return this.findAllClientsPort.findAll();
    }
}
