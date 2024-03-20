package com.jobnet.post.mappers;

import com.jobnet.post.dtos.requests.CategoryRequest;
import com.jobnet.post.dtos.responses.CategoryResponse;
import com.jobnet.post.models.Category;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoryMapper {

    public Function<CategoryRequest, Category> convertToCategory =
        categoryRequest ->
            Category.builder()
                .name(categoryRequest.getName())
                .build();

    public Function<Category, CategoryResponse> convertToCategoryResponse =
        category ->
            CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
}
