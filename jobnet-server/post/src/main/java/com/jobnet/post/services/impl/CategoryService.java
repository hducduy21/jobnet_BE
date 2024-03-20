package com.jobnet.post.services.impl;

import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.post.dtos.requests.CategoryRequest;
import com.jobnet.post.dtos.responses.CategoryResponse;
import com.jobnet.post.mappers.CategoryMapper;
import com.jobnet.post.models.Category;
import com.jobnet.post.repositories.CategoryRepository;
import com.jobnet.post.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getCategories(String search) {
        List<Category> categories = StringUtils.isBlank(search)
            ? categoryRepository.findAll()
            : categoryRepository.findByNameContainsIgnoreCase(search);
        List<CategoryResponse> responses = categories.stream()
            .map(this::getCategoryResponse)
            .toList();
        log.info("Get categories - search={}: {}", search, responses);
        return responses;
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        CategoryResponse response = this.getCategoryResponse(category);
        log.info("Get category by id - id={}: {}", id, response);
        return response;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        Category _category = categoryMapper.convertToCategory.apply(categoryRequest);
        Category category = categoryRepository.save(_category);
        CategoryResponse response = this.getCategoryResponse(category);
        log.info("Create category: {}", response);
        return response;
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        if (categoryRepository.existsByIdNotAndName(id, categoryRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        CategoryResponse response = this.getCategoryResponse(category);
        log.info("Update category - id={}: {}", id, response);
        return response;
    }

    @Override
    public void deleteCategoryById(String id) {
        if (!categoryRepository.existsById(id))
            throw new ResourceNotFoundException("Category not found.");
        categoryRepository.deleteById(id);
        log.info("Delete category by id - id={}", id);
    }

    private CategoryResponse getCategoryResponse(Category category) {
        return categoryMapper.convertToCategoryResponse.apply(category);
    }
}
