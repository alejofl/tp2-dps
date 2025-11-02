package com.rt.springboot.app.adapters.driven.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaClientRepository extends JpaRepository<ClientRelationalEntity, Long> {

    ClientRelationalEntity findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
