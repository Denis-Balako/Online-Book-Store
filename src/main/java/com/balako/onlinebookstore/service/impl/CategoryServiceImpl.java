package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.category.CategoryDto;
import com.balako.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.balako.onlinebookstore.service.CategoryService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//TODO: implement all methods
@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CategoryDto getById(Long id) {
        return null;
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
