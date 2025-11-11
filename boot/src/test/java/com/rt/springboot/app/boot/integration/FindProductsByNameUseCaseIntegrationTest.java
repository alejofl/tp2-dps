package com.rt.springboot.app.boot.integration;

import com.rt.springboot.app.adapter.driven.product.ProductRelationalEntity;
import com.rt.springboot.app.adapter.driven.product.ProductRepository;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driving.product.FindProductsByNameUseCase;
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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FindProductsByNameUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindProductsByNameUseCase findProductsByNameUseCase;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        createProduct("Laptop Dell", BigDecimal.valueOf(999.99));
        createProduct("Laptop HP", BigDecimal.valueOf(899.99));
        createProduct("Mouse Logitech", BigDecimal.valueOf(29.99));
        createProduct("Keyboard Mechanical", BigDecimal.valueOf(79.99));
        createProduct("Monitor Samsung", BigDecimal.valueOf(299.99));
    }

    private void createProduct(String name, BigDecimal price) {
        ProductRelationalEntity product = new ProductRelationalEntity();
        product.setUuid(UUID.randomUUID());
        product.setName(name);
        product.setPrice(price);
        product.setCreatedAt(Date.valueOf(LocalDate.now()));
        productRepository.save(product);
    }

    @Test
    @DisplayName("Should find products by name containing search term")
    @WithMockUser(roles = "ADMIN")
    void shouldFindProductsByNameContainingSearchTerm() throws Exception {
        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[?(@.name == 'Laptop Dell')]").exists())
                .andExpect(jsonPath("$[?(@.name == 'Laptop HP')]").exists());

        // Verify
        List<Product> products = findProductsByNameUseCase.findByName("Laptop");
        assertThat(products).isNotNull();
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::name)
                .containsExactlyInAnyOrder("Laptop Dell", "Laptop HP");
    }

    @Test
    @DisplayName("Should return empty list when no products match")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEmptyListWhenNoProductsMatch() throws Exception {
        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", "NonExistentProduct"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        // Verify
        List<Product> products = findProductsByNameUseCase.findByName("NonExistentProduct");
        assertThat(products).isNotNull();
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Should find single product by exact name")
    @WithMockUser(roles = "ADMIN")
    void shouldFindSingleProductByExactName() throws Exception {
        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", "Mouse Logitech"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Mouse Logitech"));

        // Verify
        List<Product> products = findProductsByNameUseCase.findByName("Mouse Logitech");
        assertThat(products).isNotNull();
        assertThat(products).hasSize(1);
        assertThat(products.get(0).name()).isEqualTo("Mouse Logitech");
    }

    @Test
    @DisplayName("Should find products with partial name match")
    @WithMockUser(roles = "ADMIN")
    void shouldFindProductsWithPartialNameMatch() throws Exception {
        // When, Then
        mockMvc.perform(get("/invoice/load-products/{term}", "Monitor"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Monitor Samsung"));

        // Verify
        List<Product> products = findProductsByNameUseCase.findByName("Monitor");
        assertThat(products).isNotNull();
        assertThat(products).hasSize(1);
        assertThat(products.get(0).name()).isEqualTo("Monitor Samsung");
    }
}

