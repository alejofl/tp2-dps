package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.product.ProductRepository;
import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.InvoiceItem;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.invoice.CreateInvoiceItemPort;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class CreateInvoiceItemRelationalAdapter implements CreateInvoiceItemPort {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    @Override
    public InvoiceItem create(Invoice invoice, Product product, Integer amount) {
        final var invoiceItem = new InvoiceItemRelationalEntity();
        final var productRelationalEntity = productRepository.findByUuid(product.getUuid());
        final var invoiceRelationalEntity = invoiceRepository.findByUuid(invoice.getUuid());

        invoiceItem.setProduct(productRelationalEntity);
        invoiceItem.setAmount(amount);
        invoiceItem.setInvoice(invoiceRelationalEntity);

        return InvoiceItemMapper.INSTANCE.toDomain(invoiceItemRepository.save(invoiceItem));
    }
}
