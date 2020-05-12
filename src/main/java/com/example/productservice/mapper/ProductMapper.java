package com.example.productservice.mapper;

import com.example.productservice.dao.model.Category;
import com.example.productservice.dao.model.Product;
import com.example.productservice.dto.CreateProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMapper {
    public Product createProduct(CreateProductRequestDTO source, Category category) {
        Product newProduct = new Product();
        newProduct.setCurrency(source.getCurrency());
        newProduct.setProductName(source.getProductName());
        newProduct.setPriceInCents(source.getPriceInCents());
        newProduct.setCategory(category);

        return newProduct;
    }

    public ProductResponseDTO mapToResponseProductDTO(Product savedProduct) {
        return ProductResponseDTO.builder()
                .priceWithCurrency((savedProduct.getPriceInCents() / 100f) + savedProduct.getCurrency().name())
                .productName(savedProduct.getProductName())
                .productCategoryName(Optional.ofNullable(savedProduct.getCategory().getCategoryName()).orElse(""))
                .build();

    }
}
