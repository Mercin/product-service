package com.example.productservice.unit;

import com.example.productservice.dao.model.Category;
import com.example.productservice.dto.CategoryResponseDTO;
import com.example.productservice.mapper.CategoryMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {

    @Test
    public void testCreateCategoryMapping() {

        Category category = new Category();
        category.setCategoryName("RandomName");

        CategoryMapper mapper = new CategoryMapper();
        final CategoryResponseDTO categoryResponseDTO = mapper.mapCategoryToResponse(category);

        Assertions.assertThat(categoryResponseDTO).isNotNull();
        Assertions.assertThat(categoryResponseDTO.getCategoryName()).isEqualTo(category.getCategoryName());
    }
}
