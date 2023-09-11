package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.category.CategoryDto;
import com.balako.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.balako.onlinebookstore.exception.EntityNotFoundException;
import com.balako.onlinebookstore.mapper.CategoryMapper;
import com.balako.onlinebookstore.model.Category;
import com.balako.onlinebookstore.repository.category.CategoryRepository;
import com.balako.onlinebookstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id)
        );
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
