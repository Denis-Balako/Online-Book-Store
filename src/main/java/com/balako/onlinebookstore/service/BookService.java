package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.book.response.BookDto;
import com.balako.onlinebookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.balako.onlinebookstore.dto.book.request.BookSearchParametersDto;
import com.balako.onlinebookstore.dto.book.request.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParametersDto params);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);
}
