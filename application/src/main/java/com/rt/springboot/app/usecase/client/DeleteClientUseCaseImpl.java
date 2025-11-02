package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.port.driven.ClientRepository;
import com.rt.springboot.app.port.driving.client.DeleteClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {

    private final ClientRepository clientRepository;

    @Override
    public void delete(UUID id) {
        this.clientRepository.delete(id);
    }
}
