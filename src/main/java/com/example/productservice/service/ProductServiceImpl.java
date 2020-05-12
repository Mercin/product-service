package com.example.productservice.service;

import com.example.productservice.dao.model.Category;
import com.example.productservice.dao.model.Product;
import com.example.productservice.dao.repository.CategoryRepository;
import com.example.productservice.dao.repository.ProductRepository;
import com.example.productservice.dto.CreateProductRequestDTO;
import com.example.productservice.dto.EditProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;
import com.example.productservice.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ConversionService conversionService;

    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO request) {

        log.info("Creating new product.");

        final Optional<Category> category = categoryRepository.findById(request.getCategoryId());

        if (!category.isPresent()) {
            return null;
        }

        final Product product = mapper.createProduct(request, category.get());
        final Product savedProduct = productRepository.save(product);
        return mapper.mapToResponseProductDTO(savedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {

        log.info("Deleting product with id: {}", productId);

        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponseDTO getProduct(UUID productId) {

        log.info("Fetching product with id: {}", productId);

        final Optional<Product> product = productRepository.findById(productId);
        return product.map(mapper::mapToResponseProductDTO).orElse(null);
    }

    @Override
    public ProductResponseDTO editProduct(UUID productId, EditProductRequestDTO request) {

        log.info("Editing product with id: {}", productId);

        final Optional<Product> oldProduct = productRepository.findById(productId);

        final Product product = oldProduct.map(z -> {

            if (request.getCurrency() != null) {
                try {
                    final Long newValue = conversionService.convertValue(z.getCurrency(), request.getCurrency(), z.getPriceInCents());
                    z.setPriceInCents(newValue.intValue());
                } catch (IOException e) {
                    log.warn("Failed to reach Conversion Service", e);
                }
            }

            z.setCurrency(Optional.ofNullable(request.getCurrency()).orElse(z.getCurrency()));
            z.setPriceInCents(Optional.ofNullable(request.getPriceInCents()).orElse(z.getPriceInCents()));
            z.setProductName(Optional.ofNullable(request.getProductName()).orElse(z.getProductName()));
            if (request.getCategoryId() != null) {
                final Optional<Category> foundCategory = categoryRepository.findById(request.getCategoryId());
                foundCategory.ifPresent(z::setCategory);
            }
            return productRepository.save(z);
        }).orElse(null);
        if (product != null) {
            return mapper.mapToResponseProductDTO(product);
        }
        return null;
    }
}
