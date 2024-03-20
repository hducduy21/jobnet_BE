package com.jobnet.post.controllers;

import com.jobnet.post.dtos.requests.CategoryRequest;
import com.jobnet.post.dtos.responses.CategoryResponse;
import com.jobnet.post.services.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getCategories(@RequestParam(required = false) String search) {
        List<CategoryResponse> categoryResponses = categoryService.getCategories(search);
        log.info("Get categories successfully");
        return categoryResponses;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategoryById(@PathVariable String id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        log.info("Get category by id successfully");
        return categoryResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        log.info("Create category successfully");
        return categoryResponse;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse updateCategory(
        @PathVariable String id,
        @RequestBody @Valid CategoryRequest categoryRequest
    ) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryRequest);
        log.info("Update category successfully");
        return categoryResponse;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategoryById(id);
        log.info("Delete category successfully");
    }
}
