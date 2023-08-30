package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.BookDto;
import com.balako.onlinebookstore.dto.BookSearchParametersDto;
import com.balako.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParametersDto params);
}
