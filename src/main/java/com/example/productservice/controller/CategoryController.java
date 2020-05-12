package com.example.productservice.controller;

import com.example.productservice.dto.CategoriesResponseDTO;
import com.example.productservice.dto.CategoryResponseDTO;
import com.example.productservice.dto.CreateCategoryRequestDTO;
import com.example.productservice.dto.EditCategoryRequestDTO;
import com.example.productservice.dto.ProductsResponseDTO;
import com.example.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CreateCategoryRequestDTO request) {
        return ResponseEntity.ok(service.createCategory(request));
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "categoryId") UUID categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> editCategory(@PathVariable(value = "categoryId") UUID categoryId, @RequestBody EditCategoryRequestDTO request) {
        return ResponseEntity.ok(service.editCategory(categoryId, request));
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable(value = "categoryId") UUID categoryId) {
        return ResponseEntity.ok(service.getCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<CategoriesResponseDTO> getCategories() {
        return ResponseEntity.ok(service.getCategories(100, 0));
    }

    @GetMapping(value = "/{categoryId}/products")
    public ResponseEntity<ProductsResponseDTO> getAllProductsInCategory(@PathVariable(value = "categoryId") UUID categoryId) {
        return ResponseEntity.ok(service.getProductsInCategory(categoryId));
    }
}
