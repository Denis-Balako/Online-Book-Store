package com.balako.onlinebookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.balako.onlinebookstore.model.Book;
import com.balako.onlinebookstore.model.CartItem;
import com.balako.onlinebookstore.repository.cartitem.CartItemRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartItemRepositoryTest {
    private static final CartItem CART_ITEM_1 = new CartItem();
    private static final CartItem CART_ITEM_2 = new CartItem();
    private static final Book VALID_BOOK_1 = new Book();
    private static final Book VALID_BOOK_2 = new Book();
    private static final String ADD_BOOKS_AND_CATEGORIES =
            "classpath:db/book/add-book-to-book-table"
                    + "-and-category-to-category-table.sql";
    private static final String DELETE_BOOKS_AND_CATEGORIES =
            "classpath:db/book/delete-from-book-and-category-table.sql";
    private static final String ADD_USERS_AND_ROLES =
            "classpath:db/user/add-user-and-admin.sql";
    private static final String DELETE_USERS_AND_ROLES =
            "classpath:db/user/delete-from-user-and-role-table.sql";
    private static final String ADD_CARTS_AND_CART_ITEMS =
            "classpath:db/cart/add-cart-to-users.sql";
    private static final String DELETE_CARTS_AND_CART_ITEMS =
            "classpath:db/cart/delete-from-cart-and-cart-item-table.sql";
    private static final String USER_USERNAME = "user";
    @Autowired
    private CartItemRepository cartItemRepository;

    @BeforeAll
    static void beforeAll() {
        VALID_BOOK_1.setId(1L);
        VALID_BOOK_1.setTitle("Title 1");
        VALID_BOOK_1.setAuthor("Author 1");
        VALID_BOOK_1.setIsbn("978-0545010221");
        VALID_BOOK_1.setDescription("Some description");
        VALID_BOOK_1.setCoverImage("Image 1");
        VALID_BOOK_1.setCategories(new HashSet<>());
        VALID_BOOK_1.setPrice(BigDecimal.TEN);

        VALID_BOOK_2.setId(2L);
        VALID_BOOK_2.setTitle("Title 2");
        VALID_BOOK_2.setAuthor("Author 2");
        VALID_BOOK_2.setIsbn("978-0735619678");
        VALID_BOOK_2.setDescription("Some description");
        VALID_BOOK_2.setCoverImage("Image 2");
        VALID_BOOK_2.setCategories(new HashSet<>());
        VALID_BOOK_2.setPrice(BigDecimal.ONE);

        CART_ITEM_1.setId(2L);
        CART_ITEM_1.setBook(VALID_BOOK_1);
        CART_ITEM_1.setQuantity(1);

        CART_ITEM_2.setId(3L);
        CART_ITEM_2.setBook(VALID_BOOK_2);
        CART_ITEM_2.setQuantity(1);
    }

    @Test
    @DisplayName("Find a cart item by id with book")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = {ADD_BOOKS_AND_CATEGORIES,
            ADD_USERS_AND_ROLES,
            ADD_CARTS_AND_CART_ITEMS},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {DELETE_CARTS_AND_CART_ITEMS,
            DELETE_BOOKS_AND_CATEGORIES,
            DELETE_USERS_AND_ROLES},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByIdWithBook_validId_returnCartItem() {
        CartItem actual = cartItemRepository.findByIdWithBook(CART_ITEM_1.getId()).orElseThrow(
                () -> new RuntimeException("Can't find cart item with id:"
                        + CART_ITEM_1.getId()));

        assertTrue(EqualsBuilder.reflectionEquals(CART_ITEM_1, actual, "shoppingCart"));
        assertEquals(CART_ITEM_1.getBook(), actual.getBook());
    }
}
