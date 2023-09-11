package com.balako.onlinebookstore.mapper;

import com.balako.onlinebookstore.config.MapperConfig;
import com.balako.onlinebookstore.dto.book.BookDto;
import com.balako.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.balako.onlinebookstore.dto.book.CreateBookRequestDto;
import com.balako.onlinebookstore.model.Book;
import java.util.HashSet;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategories(new HashSet<>(book.getCategories()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        //TODO: implement method
        return null;
    }
}
