package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.BookDto;
import com.balako.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getById(Long id);
}
