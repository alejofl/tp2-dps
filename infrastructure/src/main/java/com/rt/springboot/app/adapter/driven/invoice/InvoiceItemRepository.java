package com.rt.springboot.app.adapter.driven.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItemRelationalEntity, Long> {
}
