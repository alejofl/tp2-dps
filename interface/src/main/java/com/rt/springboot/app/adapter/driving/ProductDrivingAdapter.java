package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driving.product.FindProductUseCase;
import com.rt.springboot.app.port.driving.product.FindProductsByNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductDrivingAdapter {
    private final FindProductsByNameUseCase findProductsByNameUseCase;
    private final FindProductUseCase findProductUseCase;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam String term) {
        return ResponseEntity.ok(findProductsByNameUseCase.findByName(term));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(findProductUseCase.findById(id));
    }
}
