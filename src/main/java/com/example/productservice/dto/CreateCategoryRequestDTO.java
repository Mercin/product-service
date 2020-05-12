package com.example.productservice.dto;

import lombok.Data;

import java.util.UUID;

import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryRequestDTO {
    @NotNull
    private String categoryName;

    private UUID parentId;
}
