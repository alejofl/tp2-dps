package com.rt.springboot.app.adapters.driven.invoice;

import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driven.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RelationalInvoiceRepository implements InvoiceRepository {

    private final JpaInvoiceRepository jpaInvoiceRepository;

    @Override
    public Invoice findById(UUID id) {
        final var result = this.jpaInvoiceRepository.findByUuid(id);
        return InvoiceMapper.INSTANCE.toDomain(result);
    }

    @Override
    public void delete(UUID id) {
        this.jpaInvoiceRepository.deleteByUuid(id);
    }
}
