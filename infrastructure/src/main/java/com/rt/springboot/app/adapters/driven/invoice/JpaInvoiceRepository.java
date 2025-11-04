package com.rt.springboot.app.adapters.driven.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaInvoiceRepository extends JpaRepository<InvoiceRelationalEntity, Long> {
    InvoiceRelationalEntity findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
