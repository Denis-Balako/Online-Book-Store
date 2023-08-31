package com.balako.onlinebookstore.repository;

import com.balako.onlinebookstore.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
