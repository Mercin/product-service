package com.example.productservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesResponseDTO {
    private List<CategoryResponseDTO> categories;
    private int page = 0;
    private boolean hasNext = false;
    private int totalPages = 1;
}
