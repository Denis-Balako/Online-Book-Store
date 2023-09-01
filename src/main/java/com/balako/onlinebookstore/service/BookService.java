package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.BookDto;
import com.balako.onlinebookstore.dto.BookSearchParametersDto;
import com.balako.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParametersDto params);
}
