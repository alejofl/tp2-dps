package com.rt.springboot.app.usecase.client;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.ClientRepository;
import com.rt.springboot.app.port.driving.client.FindAllClientsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllClientsUseCaseImpl implements FindAllClientsUseCase {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }
}
