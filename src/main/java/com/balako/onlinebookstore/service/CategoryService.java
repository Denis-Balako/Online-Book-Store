package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.category.request.CreateCategoryRequestDto;
import com.balako.onlinebookstore.dto.category.response.CategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
