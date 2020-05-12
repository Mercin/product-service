package com.example.productservice.service;

import com.example.productservice.dto.CreateProductRequestDTO;
import com.example.productservice.dto.EditProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;

import java.util.UUID;

public interface ProductService {
    ProductResponseDTO createProduct(CreateProductRequestDTO request);

    void deleteProduct(UUID productId);

    ProductResponseDTO getProduct(UUID productId);

    ProductResponseDTO editProduct(UUID productId, EditProductRequestDTO request);
}
