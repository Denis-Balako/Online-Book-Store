package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
