package com.example.productservice.service;

import com.example.productservice.dao.model.Category;
import com.example.productservice.dao.model.Product;
import com.example.productservice.dao.repository.CategoryRepository;
import com.example.productservice.dto.CategoriesResponseDTO;
import com.example.productservice.dto.CategoryResponseDTO;
import com.example.productservice.dto.CreateCategoryRequestDTO;
import com.example.productservice.dto.EditCategoryRequestDTO;
import com.example.productservice.dto.ProductsResponseDTO;
import com.example.productservice.mapper.CategoryMapper;
import com.example.productservice.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    @Override
    public void deleteCategory(UUID categoryId) {
        log.info("Deleting category with id: {}", categoryId);
        repository.deleteById(categoryId);
    }

    @Override
    public CategoryResponseDTO editCategory(UUID categoryId, EditCategoryRequestDTO request) {

        log.info("Editing category with id {}", categoryId);

        final Optional<Category> oldCategory = repository.findById(categoryId);
        final Category category = oldCategory.map(z -> {

            if (request.getParentId() != null) {
                final Optional<Category> foundParent = repository.findById(request.getParentId());
                foundParent.ifPresent(z::setParentCategory);
            }
            z.setCategoryName(Optional.ofNullable(request.getCategoryName()).orElse(z.getCategoryName()));
            return repository.saveAndFlush(z);
        }).orElse(null);

        if (category != null) {
            return categoryMapper.mapCategoryToResponse(category);
        }

        return null;
    }

    @Override
    public CategoryResponseDTO getCategory(UUID categoryId) {

        log.info("Fetching category with id: {}", categoryId);

        final Optional<Category> foundCategory = repository.findById(categoryId);
        return foundCategory.map(categoryMapper::mapCategoryToResponse).orElse(null);
    }

    @Override
    public ProductsResponseDTO getProductsInCategory(UUID categoryId) {

        log.info("Fetching products from category with id: {}", categoryId);


        ProductsResponseDTO result = new ProductsResponseDTO();

        final Optional<Category> foundCategory = repository.findById(categoryId);
        if (foundCategory.isPresent()) {
            final List<Product> products = Optional.ofNullable(foundCategory.get().getProducts()).orElse(new ArrayList<>());
            result.setProducts(products.stream().map(productMapper::mapToResponseProductDTO).collect(Collectors.toList()));
            return result;
        }
        return null;
    }

    @Override
    public CategoriesResponseDTO getCategories(int limit, int page) {

        log.info("Fetching categories");

        final Page<Category> all = repository.findAll(PageRequest.of(page, limit));
        if (all.hasContent()) {
            return categoryMapper.mapCategoriesResponse(all);
        }
        return null;
    }

    @Override
    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO request) {

        log.info("Creating category.");

        Category parent = null;
        if (request.getParentId() != null) {
            final Optional<Category> foundCategory = repository.findById(request.getParentId());
            if (foundCategory.isPresent()) {
                parent = foundCategory.get();
            }
        }

        final Category newCategory = new Category();
        newCategory.setCategoryName(request.getCategoryName());
        newCategory.setParentCategory(parent);
        return categoryMapper.mapCategoryToResponse(repository.save(newCategory));
    }
}
