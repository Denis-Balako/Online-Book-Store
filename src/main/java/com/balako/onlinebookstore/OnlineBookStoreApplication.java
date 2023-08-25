package com.balako.onlinebookstore;

import com.balako.onlinebookstore.model.Book;
import com.balako.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    private final BookService bookService;

    @Autowired
    public OnlineBookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book effectiveJava = new Book();
            effectiveJava.setTitle("Effective Java (3rd edition)");
            effectiveJava.setAuthor("Joshua Bloch");
            effectiveJava.setIsbn("0134685997");
            effectiveJava.setPrice(BigDecimal.valueOf(36));

            bookService.save(effectiveJava);

            System.out.println(bookService.findAll());
        };
    }
}
