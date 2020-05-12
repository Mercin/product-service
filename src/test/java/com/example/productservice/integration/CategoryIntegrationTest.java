package com.example.productservice.integration;

import com.example.productservice.dao.model.Category;
import com.example.productservice.dao.repository.CategoryRepository;
import com.example.productservice.dto.CategoryResponseDTO;
import com.example.productservice.dto.CreateCategoryRequestDTO;
import com.example.productservice.dto.EditCategoryRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateCategory() {

        CreateCategoryRequestDTO request = new CreateCategoryRequestDTO();
        request.setCategoryName("CategoryName");

        ResponseEntity<CategoryResponseDTO> response =
                restTemplate.postForEntity("http://localhost:" + port + "/api/v1/categories", request, CategoryResponseDTO.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getCategoryName()).isEqualTo(request.getCategoryName());

    }

    @Test
    public void testCreateCategoryWithMissingName() {

        CreateCategoryRequestDTO request = new CreateCategoryRequestDTO();

        ResponseEntity<CategoryResponseDTO> response =
                restTemplate.postForEntity("http://localhost:" + port + "/api/v1/categories", request, CategoryResponseDTO.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testEditCategory() {
        Category category = new Category();
        category.setCategoryName("OldCategoryName");
        final Category savedCategory = repository.saveAndFlush(category);

        EditCategoryRequestDTO request = new EditCategoryRequestDTO();
        request.setCategoryName("NewCategoryName");

        /**
         * The reason why I went this way instead of using TestRestTemplate is because of a bug with
         * PATCH methods described in: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
         */

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        headers.setContentType(mediaType);

        HttpEntity<EditCategoryRequestDTO> entity = new HttpEntity<>(request, headers);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<CategoryResponseDTO> response =
                restTemplate.exchange("http://localhost:" + port + "/api/v1/categories/" + savedCategory.getId().toString(),
                HttpMethod.PATCH, entity, CategoryResponseDTO.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getCategoryName()).isEqualTo(request.getCategoryName());
        Assertions.assertThat(response.getBody().getCategoryName()).isNotEqualToIgnoringCase(category.getCategoryName());
    }

    public static <T> HttpEntity<T> createEntityWithHeadersAndBody(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<T>(body, headers);
    }
}
