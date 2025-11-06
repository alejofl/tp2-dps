package com.rt.springboot.app.adapter.driven.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientRelationalEntity, Long> {

    ClientRelationalEntity findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
