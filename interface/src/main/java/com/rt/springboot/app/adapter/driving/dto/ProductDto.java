package com.rt.springboot.app.adapter.driving.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ProductDto {
    private String id;
    private UUID uuid;
    private String name;
    private String price;
    private String createdAt;
}
