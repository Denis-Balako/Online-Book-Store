package com.balako.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.balako.onlinebookstore.dto.book.request.BookSearchParametersDto;
import com.balako.onlinebookstore.dto.book.request.CreateBookRequestDto;
import com.balako.onlinebookstore.dto.book.response.BookDto;
import com.balako.onlinebookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.balako.onlinebookstore.exception.EntityNotFoundException;
import com.balako.onlinebookstore.mapper.BookMapper;
import com.balako.onlinebookstore.model.Book;
import com.balako.onlinebookstore.model.Category;
import com.balako.onlinebookstore.repository.book.BookRepository;
import com.balako.onlinebookstore.repository.book.BookSpecificationBuilder;
import com.balako.onlinebookstore.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final Book BOOK = new Book();
    private static final Category CATEGORY = new Category();
    private static final CreateBookRequestDto REQUEST_DTO =
            new CreateBookRequestDto();
    private static final BookDtoWithoutCategoryIds DTO_WITHOUT_CATEGORY_IDS =
            new BookDtoWithoutCategoryIds();
    private static final BookDto BOOK_DTO = new BookDto();
    private static final Long BOOK_ID = 1L;
    private static final Long CATEGORY_ID = 1L;
    private static final Integer NUMBER_OF_INVOCATIONS = 1;
    private static final BookSearchParametersDto SEARCH_PARAMETERS =
            new BookSearchParametersDto(
                    new String[] {"Title 1", "Title 2"},
                    new String[] {"Author 1", "Author 2"},
                    new String[] {"978-0545010221"});
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @BeforeAll
    static void beforeAll() {
        CATEGORY.setId(CATEGORY_ID);
        CATEGORY.setName("Category 1");
        CATEGORY.setDescription("Category 1 description");

        BOOK.setId(BOOK_ID);
        BOOK.setTitle("Title 1");
        BOOK.setAuthor("Author 1");
        BOOK.setIsbn("978-0545010221");
        BOOK.setDescription("Some description");
        BOOK.setCoverImage("Image 1");
        BOOK.setCategories(Set.of(CATEGORY));
        BOOK.setPrice(BigDecimal.TEN);

        REQUEST_DTO.setTitle(BOOK.getTitle());
        REQUEST_DTO.setAuthor(BOOK.getAuthor());
        REQUEST_DTO.setIsbn(BOOK.getIsbn());
        REQUEST_DTO.setDescription(BOOK.getDescription());
        REQUEST_DTO.setCoverImage(BOOK.getCoverImage());
        REQUEST_DTO.setPrice(BOOK.getPrice());
        REQUEST_DTO.setCategoryIds(Set.of(CATEGORY.getId()));

        BOOK_DTO.setId(BOOK.getId());
        BOOK_DTO.setTitle(BOOK.getTitle());
        BOOK_DTO.setAuthor(BOOK.getAuthor());
        BOOK_DTO.setIsbn(BOOK.getIsbn());
        BOOK_DTO.setDescription(BOOK.getDescription());
        BOOK_DTO.setCoverImage(BOOK.getCoverImage());
        BOOK_DTO.setPrice(BOOK.getPrice());
        BOOK_DTO.setCategories(REQUEST_DTO.getCategoryIds());

        DTO_WITHOUT_CATEGORY_IDS.setId(BOOK.getId());
        DTO_WITHOUT_CATEGORY_IDS.setTitle(BOOK.getTitle());
        DTO_WITHOUT_CATEGORY_IDS.setAuthor(BOOK.getAuthor());
        DTO_WITHOUT_CATEGORY_IDS.setIsbn(BOOK.getIsbn());
        DTO_WITHOUT_CATEGORY_IDS.setDescription(BOOK.getDescription());
        DTO_WITHOUT_CATEGORY_IDS.setCoverImage(BOOK.getCoverImage());
        DTO_WITHOUT_CATEGORY_IDS.setPrice(BOOK.getPrice());
    }

    @Test
    @DisplayName("Test saving a book with valid request")
    public void save_validBook_returnBookDto() {
        when(bookRepository.save(any(Book.class))).thenReturn(BOOK);
        when(bookMapper.toDto(any(Book.class))).thenReturn(BOOK_DTO);
        when(bookMapper.toModel(any(CreateBookRequestDto.class)))
                .thenReturn(BOOK);

        BookDto actual = bookService.save(REQUEST_DTO);

        assertEquals(BOOK_DTO, actual);
        verify(bookRepository).save(any(Book.class));
        verify(bookMapper).toModel(any(CreateBookRequestDto.class));
        verify(bookMapper).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Test finding all books with valid request")
    public void findAll_validRequest_returnBookDtoList() {
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findAllWithCategories(pageable))
                .thenReturn(List.of(BOOK));
        when(bookMapper.toDto(any(Book.class))).thenReturn(BOOK_DTO);

        List<BookDto> actual = bookService.findAll(pageable);

        assertEquals(List.of(BOOK_DTO), actual);
        verify(bookMapper).toDto(any(Book.class));
        verify(bookRepository).findAllWithCategories(pageable);
    }

    @Test
    @DisplayName("Test getting a book by id with valid id")
    public void getById_validId_returnBookDto() {
        when(bookMapper.toDto(any(Book.class))).thenReturn(BOOK_DTO);
        when(bookRepository.findByIdWithCategories(anyLong()))
                .thenReturn(Optional.of(BOOK));

        BookDto actual = bookService.getById(BOOK_ID);

        assertEquals(BOOK_DTO, actual);
        verify(bookMapper).toDto(any(Book.class));
        verify(bookRepository).findByIdWithCategories(anyLong());
    }

    @Test
    @DisplayName("Test getting a book by id with non valid id")
    public void getById_nonValidId_returnBookDto() {
        when(bookRepository.findByIdWithCategories(anyLong()))
                .thenReturn(Optional.empty());
        String expectedMessage = "Can't find book by id: 1";

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> bookService.getById(BOOK_ID));

        assertEquals(expectedMessage, exception.getMessage());
        verify(bookRepository).findByIdWithCategories(anyLong());
        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(bookMapper);
    }

    @Test
    @DisplayName("Test deleting a book by id with valid id")
    public void deleteById_validId_deleteSuccessful() {
        assertDoesNotThrow(() -> bookService.deleteById(BOOK_ID));

        verify(bookRepository).deleteById(BOOK_ID);
    }

    @Test
    @DisplayName("Test updating a book by id with valid id")
    public void update_validId_returnBookDto() {
        when(bookRepository.save(any(Book.class))).thenReturn(BOOK);
        when(bookMapper.toDto(any(Book.class))).thenReturn(BOOK_DTO);
        when(bookMapper.toModel(any(CreateBookRequestDto.class)))
                .thenReturn(BOOK);

        BookDto actual = bookService.update(BOOK_ID, REQUEST_DTO);

        assertEquals(BOOK_DTO, actual);
        verify(bookRepository).save(any(Book.class));
        verify(bookMapper).toModel(any(CreateBookRequestDto.class));
        verify(bookMapper).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Test searching books by specification with valid dto")
    public void search_validDto_returnBookDtoList() {
        Specification<Book> specification = mock(Specification.class);
        when(bookSpecificationBuilder.build(SEARCH_PARAMETERS))
                .thenReturn(specification);
        when(bookRepository.findAll(specification)).thenReturn(List.of(BOOK));
        when(bookMapper.toDtoWithoutCategories(any(Book.class)))
                .thenReturn(DTO_WITHOUT_CATEGORY_IDS);

        List<BookDtoWithoutCategoryIds> actual = bookService.search(SEARCH_PARAMETERS);

        assertEquals(List.of(DTO_WITHOUT_CATEGORY_IDS), actual);
        verify(bookSpecificationBuilder, times(NUMBER_OF_INVOCATIONS))
                .build(SEARCH_PARAMETERS);
        verify(bookRepository, times(NUMBER_OF_INVOCATIONS))
                .findAll(specification);
        verify(bookMapper, times(NUMBER_OF_INVOCATIONS))
                .toDtoWithoutCategories(any(Book.class));
    }

    @Test
    @DisplayName("Test finding books by valid category id")
    public void findAllByCategoryId_validId_returnBookDtoList() {
        when(bookRepository.findAllByCategoryId(anyLong()))
                .thenReturn(List.of(BOOK));
        when(bookMapper.toDtoWithoutCategories(any(Book.class)))
                .thenReturn(DTO_WITHOUT_CATEGORY_IDS);

        List<BookDtoWithoutCategoryIds> actual =
                bookService.findAllByCategoryId(CATEGORY_ID);

        assertEquals(List.of(DTO_WITHOUT_CATEGORY_IDS), actual);
        verify(bookMapper).toDtoWithoutCategories(any(Book.class));
        verify(bookRepository).findAllByCategoryId(anyLong());
    }
}
