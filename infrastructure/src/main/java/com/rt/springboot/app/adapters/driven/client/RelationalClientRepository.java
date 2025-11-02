package com.rt.springboot.app.adapters.driven.client;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driven.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RelationalClientRepository implements ClientRepository {

    private final JpaClientRepository jpaClientRepository;

    @Override
    public List<Client> findAll() {
        return this.jpaClientRepository
                .findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public Client findById(UUID id) {
        final var result = this.jpaClientRepository
                .findByUuid(id);

        return ClientMapper.INSTANCE.toDomain(result);
    }

    @Override
    public void delete(UUID id) {
        this.jpaClientRepository.deleteByUuid(id);
    }
}
