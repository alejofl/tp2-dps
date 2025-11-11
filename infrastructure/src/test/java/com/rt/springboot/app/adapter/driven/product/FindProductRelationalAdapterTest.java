package com.rt.springboot.app.adapter.driven.product;

import com.rt.springboot.app.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProductRelationalAdapterTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private FindProductRelationalAdapter adapter;

    @Test
    void shouldFindProductById() {
        UUID id = UUID.randomUUID();

        ProductRelationalEntity entity = new ProductRelationalEntity();
        entity.setUuid(id);
        entity.setName("Laptop");
        entity.setPrice(new BigDecimal("1500"));

        when(repository.findByUuid(id)).thenReturn(entity);

        Product result = adapter.findById(id);

        verify(repository).findByUuid(id);

        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(id);
        assertThat(result.name()).isEqualTo("Laptop");
        assertThat(result.price()).isEqualTo(new BigDecimal("1500"));
    }
}