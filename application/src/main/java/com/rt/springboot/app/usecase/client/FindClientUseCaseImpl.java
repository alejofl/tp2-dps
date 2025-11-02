package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.ClientRepository;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindClientUseCaseImpl implements FindClientUseCase {

    private final ClientRepository clientRepository;

    @Override
    public Client findById(UUID id) {
        return this.clientRepository.findById(id);
    }
}
