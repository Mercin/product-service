package com.example.productservice.controller;

import com.example.productservice.dto.CreateProductRequestDTO;
import com.example.productservice.dto.EditProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;
import com.example.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO request) {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "productId") UUID productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductResponseDTO> editProduct(@PathVariable(value = "productId") UUID productId, @RequestBody EditProductRequestDTO request) {
        final ProductResponseDTO productResponseDTO = service.editProduct(productId, request);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable(value = "productId") UUID productId) {
        return ResponseEntity.ok(service.getProduct(productId));
    }
}
