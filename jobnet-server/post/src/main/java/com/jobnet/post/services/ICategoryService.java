package com.jobnet.post.services;

import com.jobnet.post.dtos.requests.CategoryRequest;
import com.jobnet.post.dtos.responses.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getCategories(String search);

    CategoryResponse getCategoryById(String id);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(String id, CategoryRequest categoryRequest);

    void deleteCategoryById(String id);
}
