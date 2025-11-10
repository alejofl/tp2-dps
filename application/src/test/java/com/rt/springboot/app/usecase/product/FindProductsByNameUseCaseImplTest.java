package com.rt.springboot.app.usecase.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.product.FindProductsByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindProductsByNameUseCaseImplTest {

    @Mock
    private FindProductsByNamePort findProductsByNamePort;

    @InjectMocks
    private FindProductsByNameUseCaseImpl findProductsByNameUseCase;

    private String searchTerm;

    @BeforeEach
    void setUp() {
        this.searchTerm = "Product 2";
    }

    @Test
    @DisplayName("Should return list of products when products exist with matching name")
    void shouldReturnListOfProductsWhenProductsExistWithMatchingName() {
        // Given
        List<Product> expectedProducts = createMockProducts(3);
        when(findProductsByNamePort.findByName(searchTerm)).thenReturn(expectedProducts);

        // When
        List<Product> result = findProductsByNameUseCase.findByName(searchTerm);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedProducts, result);
    }

    @Test
    @DisplayName("Should return empty list when no products exist with matching name")
    void shouldReturnEmptyListWhenNoProductsExistWithMatchingName() {
        // Given
        when(findProductsByNamePort.findByName(searchTerm)).thenReturn(new ArrayList<>());

        // When
        List<Product> result = findProductsByNameUseCase.findByName(searchTerm);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private List<Product> createMockProducts(int count) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Product product = new Product(
                    UUID.randomUUID(),
                    "Product " + i,
                    BigDecimal.valueOf(100.00 + i),
                    LocalDate.now()
            );
            products.add(product);
        }
        return products;
    }

}

