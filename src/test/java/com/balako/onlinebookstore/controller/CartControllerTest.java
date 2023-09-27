package com.balako.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.balako.onlinebookstore.dto.cart.request.CreateCartItemDto;
import com.balako.onlinebookstore.dto.cart.response.CartDto;
import com.balako.onlinebookstore.dto.cart.response.CartItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest {
    protected static MockMvc mockMvc;
    private static final CartItemDto CART_ITEM_DTO_1 = new CartItemDto();
    private static final CartItemDto CART_ITEM_DTO_2 = new CartItemDto();
    private static final CartDto CART_DTO = new CartDto();
    private static final CreateCartItemDto REQUEST_DTO_1 =
            new CreateCartItemDto();
    private static final CreateCartItemDto REQUEST_DTO_2 =
            new CreateCartItemDto();
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
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        CART_ITEM_DTO_1.setId(2L);
        CART_ITEM_DTO_1.setBookId(1L);
        CART_ITEM_DTO_1.setQuantity(1);
        CART_ITEM_DTO_1.setBookTitle("Title 1");

        CART_ITEM_DTO_2.setId(3L);
        CART_ITEM_DTO_2.setBookId(2L);
        CART_ITEM_DTO_2.setQuantity(1);
        CART_ITEM_DTO_2.setBookTitle("Title 2");

        REQUEST_DTO_1.setBookId(CART_ITEM_DTO_1.getBookId());
        REQUEST_DTO_1.setQuantity(CART_ITEM_DTO_1.getQuantity());

        REQUEST_DTO_2.setBookId(3L);
        REQUEST_DTO_2.setQuantity(1);

        CART_DTO.setId(2L);
        CART_DTO.setUserId(2L);
        CART_DTO.setCartItems(Set.of(CART_ITEM_DTO_1, CART_ITEM_DTO_2));
    }

    @Test
    @DisplayName("Test creating cart item with valid request dto")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = {ADD_BOOKS_AND_CATEGORIES,
            ADD_USERS_AND_ROLES,
            ADD_CARTS_AND_CART_ITEMS},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {DELETE_CARTS_AND_CART_ITEMS,
            DELETE_BOOKS_AND_CATEGORIES,
            DELETE_USERS_AND_ROLES},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_validRequestDto_returnCartItemDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(REQUEST_DTO_1);

        MvcResult result = mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        CartItemDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CartItemDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(CART_ITEM_DTO_1, actual, "id"));
    }

    @Test
    @DisplayName("Test getting cart for valid user")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = {ADD_BOOKS_AND_CATEGORIES,
            ADD_USERS_AND_ROLES,
            ADD_CARTS_AND_CART_ITEMS},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {DELETE_CARTS_AND_CART_ITEMS,
            DELETE_BOOKS_AND_CATEGORIES,
            DELETE_USERS_AND_ROLES},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getCart_validUser_returnCartDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CartDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CartDto.class);

        assertEquals(CART_DTO, actual);
    }

    @Test
    @DisplayName("Test updating cart item with valid request dto")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = {ADD_BOOKS_AND_CATEGORIES,
            ADD_USERS_AND_ROLES,
            ADD_CARTS_AND_CART_ITEMS},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {DELETE_CARTS_AND_CART_ITEMS,
            DELETE_BOOKS_AND_CATEGORIES,
            DELETE_USERS_AND_ROLES},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_validRequestDto_returnCartItemDto() throws Exception {
        REQUEST_DTO_2.setQuantity(10);
        String jsonRequest = objectMapper.writeValueAsString(REQUEST_DTO_1);

        MvcResult result = mockMvc.perform(put("/api/cart/cart-items/"
                        + CART_ITEM_DTO_2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        CartItemDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CartItemDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(CART_ITEM_DTO_2, actual, "id"));
    }

    @Test
    @DisplayName("Test deleting cart item with valid id")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = {ADD_BOOKS_AND_CATEGORIES,
            ADD_USERS_AND_ROLES,
            ADD_CARTS_AND_CART_ITEMS},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {DELETE_CARTS_AND_CART_ITEMS,
            DELETE_BOOKS_AND_CATEGORIES,
            DELETE_USERS_AND_ROLES},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_validId_successful() throws Exception {
        mockMvc.perform(
                        delete("/api/cart/cart-items/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}
