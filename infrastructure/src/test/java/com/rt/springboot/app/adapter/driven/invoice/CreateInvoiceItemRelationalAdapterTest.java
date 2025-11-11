package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.product.ProductRelationalEntity;
import com.rt.springboot.app.adapter.driven.product.ProductRepository;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.InvoiceItem;
import com.rt.springboot.app.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateInvoiceItemRelationalAdapterTest {
    @Mock
    private InvoiceItemRepository invoiceItemRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CreateInvoiceItemRelationalAdapter adapter;

    @Test
    void shouldCreateInvoiceItem() {
        UUID invoiceId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Invoice invoice = new Invoice(invoiceId, "desc", "obs", LocalDate.of(2024, 1, 1), null, null);
        Product product = new Product(productId, "Mouse", new BigDecimal("1000"), LocalDate.of(2024, 1, 1));

        InvoiceRelationalEntity invoiceEntity = new InvoiceRelationalEntity();
        invoiceEntity.setUuid(invoiceId);
        invoiceEntity.setDescription("desc");
        invoiceEntity.setCreatedAt(Date.valueOf(LocalDate.of(2024, 1, 1)));

        ProductRelationalEntity productEntity = new ProductRelationalEntity();
        productEntity.setUuid(productId);
        productEntity.setName("Mouse");
        productEntity.setPrice(new BigDecimal("1000"));

        InvoiceItemRelationalEntity savedItem = new InvoiceItemRelationalEntity();
        savedItem.setAmount(3);
        savedItem.setInvoice(invoiceEntity);
        savedItem.setProduct(productEntity);

        when(invoiceRepository.findByUuid(invoiceId)).thenReturn(invoiceEntity);
        when(productRepository.findByUuid(productId)).thenReturn(productEntity);
        when(invoiceItemRepository.save(any())).thenReturn(savedItem);

        InvoiceItem result = adapter.create(invoice, product, 3);

        verify(invoiceRepository).findByUuid(invoiceId);
        verify(productRepository).findByUuid(productId);
        verify(invoiceItemRepository).save(any());

        assertThat(result).isNotNull();
        assertThat(result.amount()).isEqualTo(3);
        assertThat(result.product().uuid()).isEqualTo(productId);
        assertThat(result.product().name()).isEqualTo("Mouse");

    }
}
