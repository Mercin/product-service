package com.example.productservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EditCategoryRequestDTO {
    private String categoryName;
    private UUID parentId;
}
