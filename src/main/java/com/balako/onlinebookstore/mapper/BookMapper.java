package com.balako.onlinebookstore.mapper;

import com.balako.onlinebookstore.config.MapperConfig;
import com.balako.onlinebookstore.dto.book.BookDto;
import com.balako.onlinebookstore.dto.book.CreateBookRequestDto;
import com.balako.onlinebookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);
}
