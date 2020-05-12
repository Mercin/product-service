package com.example.productservice.service;

import com.example.productservice.dto.CategoriesResponseDTO;
import com.example.productservice.dto.CategoryResponseDTO;
import com.example.productservice.dto.CreateCategoryRequestDTO;
import com.example.productservice.dto.EditCategoryRequestDTO;
import com.example.productservice.dto.ProductsResponseDTO;

import java.util.UUID;

public interface CategoryService {
    void deleteCategory(UUID categoryId);

    CategoryResponseDTO editCategory(UUID categoryId, EditCategoryRequestDTO request);

    CategoryResponseDTO getCategory(UUID categoryId);

    ProductsResponseDTO getProductsInCategory(UUID categoryId);

    CategoriesResponseDTO getCategories(int limit, int page);

    CategoryResponseDTO createCategory(CreateCategoryRequestDTO request);
}