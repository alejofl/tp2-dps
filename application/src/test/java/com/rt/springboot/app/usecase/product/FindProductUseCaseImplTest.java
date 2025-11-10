package com.rt.springboot.app.usecase.product;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driven.product.FindProductPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindProductUseCaseImplTest {

    @Mock
    private FindProductPort findProductPort;

    @InjectMocks
    private FindProductUseCaseImpl findProductUseCase;

    private UUID productId;
    private String name;
    private BigDecimal price;
    private LocalDate createdDate;

    @BeforeEach
    void setUp() {
        this.productId = UUID.randomUUID();
        this.name = "Product name";
        this.price = BigDecimal.valueOf(100.00);
        this.createdDate = LocalDate.now();
    }

    @Test
    @DisplayName("Should find product by id when product exists")
    void shouldFindProductByIdWhenProductExists() {
        // Given
        Product expectedProduct = new Product(
                this.productId,
                this.name,
                this.price,
                this.createdDate
        );

        when(findProductPort.findById(productId)).thenReturn(expectedProduct);

        // When
        Product result = findProductUseCase.findById(productId);

        // Then
        assertNotNull(result);
        assertEquals(expectedProduct.uuid(), result.uuid());
        assertEquals(name, result.name());
        assertEquals(price, result.price());
        assertEquals(createdDate, result.createdAt());
    }

}

