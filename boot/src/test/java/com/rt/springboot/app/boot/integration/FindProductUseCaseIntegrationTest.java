package com.rt.springboot.app.boot.integration;

import com.rt.springboot.app.adapter.driven.product.ProductRelationalEntity;
import com.rt.springboot.app.adapter.driven.product.ProductRepository;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driving.product.FindProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvcx
@ActiveProfiles("test")
@Transactional
class FindProductUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindProductUseCase findProductUseCase;

    @Autowired
    private ProductRepository productRepository;

    private UUID productId;
    private String productName;
    private BigDecimal productPrice;
    private LocalDate createdDate;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        productName = "Test Product";
        productPrice = BigDecimal.valueOf(99.99);
        createdDate = LocalDate.now();

        ProductRelationalEntity product = new ProductRelationalEntity();
        product.setUuid(productId);
        product.setName(productName);
        product.setPrice(productPrice);
        product.setCreatedAt(Date.valueOf(createdDate));
        productRepository.save(product);
    }

    @Test
    @DisplayName("Should find product by name when product exists")
    @WithMockUser(roles = "ADMIN")
    void shouldFindProductByNameWhenProductExists() throws Exception {
        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", productName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(productName))
                .andExpect(jsonPath("$[0].uuid").value(productId.toString()));

        // Verify
        Product foundProduct = findProductUseCase.findById(productId);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.uuid()).isEqualTo(productId);
        assertThat(foundProduct.name()).isEqualTo(productName);
        assertThat(foundProduct.price()).isEqualByComparingTo(productPrice);
        assertThat(foundProduct.createdAt()).isEqualTo(createdDate);
    }

    @Test
    @DisplayName("Should return empty list when product does not exist")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEmptyListWhenProductDoesNotExist() throws Exception {
        // Given
        String nonExistentName = "NonExistentProduct";

        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", nonExistentName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        // Verify
        UUID nonExistentId = UUID.randomUUID();
        Product foundProduct = findProductUseCase.findById(nonExistentId);
        assertThat(foundProduct).isNull();
    }

    @Test
    @DisplayName("Should find multiple products by their names")
    @WithMockUser(roles = "ADMIN")
    void shouldFindMultipleProductsByTheirNames() throws Exception {
        // Given
        ProductRelationalEntity product1 = new ProductRelationalEntity();
        product1.setUuid(UUID.randomUUID());
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(100.00));
        product1.setCreatedAt(Date.valueOf(LocalDate.now()));
        product1 = productRepository.save(product1);

        ProductRelationalEntity product2 = new ProductRelationalEntity();
        product2.setUuid(UUID.randomUUID());
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(200.00));
        product2.setCreatedAt(Date.valueOf(LocalDate.now()));
        product2 = productRepository.save(product2);

        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", "Product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3)); // Includes "Test Product" from setUp

        // Verify
        Product foundProduct1 = findProductUseCase.findById(product1.getUuid());
        Product foundProduct2 = findProductUseCase.findById(product2.getUuid());

        assertThat(foundProduct1).isNotNull();
        assertThat(foundProduct2).isNotNull();
        assertThat(foundProduct1.uuid()).isNotEqualTo(foundProduct2.uuid());
        assertThat(foundProduct1.name()).isEqualTo("Product 1");
        assertThat(foundProduct2.name()).isEqualTo("Product 2");
    }
}

