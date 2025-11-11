package com.rt.springboot.app.adapter.driven.product;

import com.rt.springboot.app.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProductsByNameRelationalAdapterTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private FindProductsByNameRelationalAdapter adapter;

    @Test
    void shouldFindProductsByNameContainingTerm() {
        String term = "lap";

        ProductRelationalEntity p1 = new ProductRelationalEntity();
        p1.setUuid(java.util.UUID.randomUUID());
        p1.setName("Laptop");
        p1.setPrice(new BigDecimal("1500"));

        ProductRelationalEntity p2 = new ProductRelationalEntity();
        p2.setUuid(java.util.UUID.randomUUID());
        p2.setName("Lapdesk Cooler");
        p2.setPrice(new BigDecimal("300"));

        when(repository.findByNameContaining(term)).thenReturn(List.of(p1, p2));

        List<Product> result = adapter.findByName(term);

        verify(repository).findByNameContaining(term);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).name()).isEqualTo("Laptop");
        assertThat(result.get(0).price()).isEqualByComparingTo("1500");

        assertThat(result.get(1).name()).isEqualTo("Lapdesk Cooler");
        assertThat(result.get(1).price()).isEqualByComparingTo("300");
    }
}
