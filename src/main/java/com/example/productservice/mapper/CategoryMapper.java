package com.example.productservice.mapper;

import com.example.productservice.dao.model.Category;
import com.example.productservice.dto.CategoriesResponseDTO;
import com.example.productservice.dto.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryResponseDTO mapCategoryToResponse(Category savedCategory) {
        return CategoryResponseDTO.builder()
                .categoryName(savedCategory.getCategoryName())
                .id(savedCategory.getId())
                .build();
    }

    public CategoriesResponseDTO mapCategoriesResponse(Page<Category> source) {
        CategoriesResponseDTO result = new CategoriesResponseDTO();
        result.setCategories(source.stream().map(this::mapCategoryToResponse).collect(Collectors.toList()));
        result.setPage(source.getNumber());
        result.setHasNext(source.hasNext());
        result.setTotalPages(source.getTotalPages());
        return result;
    }
}
