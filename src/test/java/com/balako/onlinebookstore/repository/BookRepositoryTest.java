package com.balako.onlinebookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.balako.onlinebookstore.model.Book;
import com.balako.onlinebookstore.model.Category;
import com.balako.onlinebookstore.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    private static final Category VALID_CATEGORY_1 = new Category();
    private static final Category VALID_CATEGORY_2 = new Category();
    private static final Book VALID_BOOK_1 = new Book();
    private static final Book VALID_BOOK_2 = new Book();
    private static final String SQL_SCRIPT_BEFORE_METHOD =
            "classpath:db/book/add-book-to-book-table"
                    + "-and-category-to-category-table.sql";
    private static final String SQL_SCRIPT_AFTER_METHOD =
            "classpath:db/book/delete-from-book-and-category-table.sql";

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        VALID_CATEGORY_1.setId(1L);
        VALID_CATEGORY_1.setName("Category 1");
        VALID_CATEGORY_1.setDescription("Category 1 description");

        VALID_CATEGORY_2.setId(2L);
        VALID_CATEGORY_2.setName("Category 2");
        VALID_CATEGORY_2.setDescription("Category 2 description");

        VALID_BOOK_1.setId(1L);
        VALID_BOOK_1.setTitle("Title 1");
        VALID_BOOK_1.setAuthor("Author 1");
        VALID_BOOK_1.setIsbn("978-0545010221");
        VALID_BOOK_1.setDescription("Some description");
        VALID_BOOK_1.setCoverImage("Image 1");
        VALID_BOOK_1.setCategories(Set.of(VALID_CATEGORY_1, VALID_CATEGORY_2));
        VALID_BOOK_1.setPrice(BigDecimal.TEN);

        VALID_BOOK_2.setId(2L);
        VALID_BOOK_2.setTitle("Title 2");
        VALID_BOOK_2.setAuthor("Author 2");
        VALID_BOOK_2.setIsbn("978-0735619678");
        VALID_BOOK_2.setDescription("Some description");
        VALID_BOOK_2.setCoverImage("Image 2");
        VALID_BOOK_2.setCategories(Set.of(VALID_CATEGORY_2));
        VALID_BOOK_2.setPrice(BigDecimal.ONE);
    }

    @Test
    @DisplayName("Find a book by id with categories")
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByIdWithCategories_validId_returnBook() {
        Book actual = bookRepository.findByIdWithCategories(1L).orElseThrow(
                () -> new RuntimeException("Can't find book with id: 1"));

        assertEquals(VALID_BOOK_1, actual);
        assertTrue(VALID_BOOK_1.getCategories().contains(VALID_CATEGORY_1));
    }

    @Test
    @DisplayName("Find books by category id")
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCategoryId_validId_returnBookList() {
        List<Book> books = bookRepository.findAllByCategoryId(2L);

        assertEquals(2, books.size());
        assertTrue(books.contains(VALID_BOOK_1)
                && books.contains(VALID_BOOK_2));
    }

    @Test
    @DisplayName("Find all books with categories")
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllWithCategories_validTwoBooks_returnBookList() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> books = bookRepository.findAllWithCategories(pageable);

        assertEquals(2, books.size());
        assertTrue(books.contains(VALID_BOOK_1)
                && books.contains(VALID_BOOK_2));
        assertEquals(books.get(0).getCategories(), VALID_BOOK_1.getCategories());
        assertEquals(books.get(1).getCategories(), VALID_BOOK_2.getCategories());
    }
}
