package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.BookDto;
import com.balako.onlinebookstore.dto.CreateBookRequestDto;
import com.balako.onlinebookstore.exceptions.EntityNotFoundException;
import com.balako.onlinebookstore.mapper.BookMapper;
import com.balako.onlinebookstore.model.Book;
import com.balako.onlinebookstore.repository.BookRepository;
import com.balako.onlinebookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }
}
