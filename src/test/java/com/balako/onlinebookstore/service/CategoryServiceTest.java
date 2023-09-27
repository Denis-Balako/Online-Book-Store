package com.balako.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.balako.onlinebookstore.dto.category.request.CreateCategoryRequestDto;
import com.balako.onlinebookstore.dto.category.response.CategoryDto;
import com.balako.onlinebookstore.exception.EntityNotFoundException;
import com.balako.onlinebookstore.mapper.CategoryMapper;
import com.balako.onlinebookstore.model.Category;
import com.balako.onlinebookstore.repository.category.CategoryRepository;
import com.balako.onlinebookstore.service.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private static final Category CATEGORY = new Category();
    private static final CategoryDto CATEGORY_DTO = new CategoryDto();
    private static final CreateCategoryRequestDto REQUEST_DTO =
            new CreateCategoryRequestDto();
    private static final Long CATEGORY_ID = 1L;
    private static final Integer NUMBER_OF_INVOCATIONS = 1;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @BeforeAll
    static void beforeAll() {
        CATEGORY.setId(1L);
        CATEGORY.setName("Category 1");
        CATEGORY.setDescription("Category 1 description");

        CATEGORY_DTO.setId(CATEGORY.getId());
        CATEGORY_DTO.setName(CATEGORY.getName());
        CATEGORY_DTO.setDescription(CATEGORY.getDescription());

        REQUEST_DTO.setName(CATEGORY.getName());
        REQUEST_DTO.setDescription(CATEGORY.getDescription());
    }

    @Test
    @DisplayName("Test finding all categories with valid request")
    public void findAll_validRequest_returnCategoryDtoList() {
        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Category> categoryPage =
                new PageImpl<>(List.of(CATEGORY), pageable, 1);
        when(categoryRepository.findAll(pageable))
                .thenReturn(categoryPage);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(CATEGORY_DTO);

        List<CategoryDto> actual = categoryService.findAll(pageable);

        assertEquals(List.of(CATEGORY_DTO), actual);
        verify(categoryMapper).toDto(any(Category.class));
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Test getting a category by id with valid id")
    public void getById_validId_returnCategoryDto() {
        when(categoryMapper.toDto(any(Category.class))).thenReturn(CATEGORY_DTO);
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(CATEGORY));

        CategoryDto actual = categoryService.getById(CATEGORY_ID);

        assertEquals(CATEGORY_DTO, actual);
        verify(categoryMapper).toDto(any(Category.class));
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test getting a category by id with non valid id")
    public void getById_nonValidId_returnCategoryDto() {
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        String expectedMessage = "Can't find category by id: 1";

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> categoryService.getById(CATEGORY_ID));

        assertEquals(expectedMessage, exception.getMessage());
        verify(categoryRepository).findById(anyLong());
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("Test saving a category with valid request")
    public void save_validBook_returnCategoryDto() {
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(CATEGORY);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(CATEGORY_DTO);
        when(categoryMapper.toModel(any(CreateCategoryRequestDto.class)))
                .thenReturn(CATEGORY);

        CategoryDto actual = categoryService.save(REQUEST_DTO);

        assertEquals(CATEGORY_DTO, actual);
        verify(categoryRepository).save(any(Category.class));
        verify(categoryMapper).toModel(any(CreateCategoryRequestDto.class));
        verify(categoryMapper).toDto(any(Category.class));
    }

    @Test
    @DisplayName("Test deleting a category by id with valid id")
    public void deleteById_validId_deleteSuccessful() {
        assertDoesNotThrow(() -> categoryService.deleteById(CATEGORY_ID));

        verify(categoryRepository).deleteById(CATEGORY_ID);
    }

    @Test
    @DisplayName("Test updating a category by id with valid id")
    public void update_validId_returnCategoryDto() {
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(CATEGORY);
        when(categoryMapper.toDto(any(Category.class)))
                .thenReturn(CATEGORY_DTO);
        when(categoryMapper.toModel(any(CreateCategoryRequestDto.class)))
                .thenReturn(CATEGORY);

        CategoryDto actual = categoryService.update(CATEGORY_ID, REQUEST_DTO);

        assertEquals(CATEGORY_DTO, actual);
        verify(categoryRepository).save(any(Category.class));
        verify(categoryMapper).toModel(any(CreateCategoryRequestDto.class));
        verify(categoryMapper).toDto(any(Category.class));
    }
}
