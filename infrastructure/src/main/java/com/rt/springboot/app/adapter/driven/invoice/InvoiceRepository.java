package com.rt.springboot.app.adapter.driven.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<InvoiceRelationalEntity, Long> {
    InvoiceRelationalEntity findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<InvoiceRelationalEntity> findByClientUuid(UUID clientUuid);
}
