package com.balako.onlinebookstore.controller;

import com.balako.onlinebookstore.dto.category.CategoryDto;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: implement all methods
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return null;
    }

    public List<CategoryDto> getAll() {
        return Collections.emptyList();
    }

    public CategoryDto getCategoryById(Long id) {
        return null;
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return null;
    }

    public void deleteCategory(Long id) {}

    public List<CategoryDto> getBooksByCategoryId(Long id) {
        return Collections.emptyList();
    }
}
