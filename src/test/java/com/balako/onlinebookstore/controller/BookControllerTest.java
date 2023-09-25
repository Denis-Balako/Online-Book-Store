package com.balako.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.balako.onlinebookstore.dto.book.request.BookSearchParametersDto;
import com.balako.onlinebookstore.dto.book.request.CreateBookRequestDto;
import com.balako.onlinebookstore.dto.book.response.BookDto;
import com.balako.onlinebookstore.model.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
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
public class BookControllerTest {
    protected static MockMvc mockMvc;
    private static final CreateBookRequestDto REQUEST_DTO_1 =
            new CreateBookRequestDto();
    private static final CreateBookRequestDto REQUEST_DTO_2 =
            new CreateBookRequestDto();
    private static final Category VALID_CATEGORY_1 = new Category();
    private static final Category VALID_CATEGORY_2 = new Category();
    private static final BookDto VALID_BOOK_DTO_1 = new BookDto();
    private static final BookDto VALID_BOOK_DTO_2 = new BookDto();
    private static final BookSearchParametersDto SEARCH_PARAMETERS =
            new BookSearchParametersDto(
                    new String[] {"Title 1", "Title 2"},
                    new String[] {"Author 1", "Author 2"},
                    new String[] {"978-0545010221"});
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_USERNAME = "user";
    private static final String SQL_SCRIPT_BEFORE_METHOD =
            "classpath:db/book/add-book-to-book-table"
                    + "-and-category-to-category-table.sql";
    private static final String SQL_SCRIPT_AFTER_METHOD =
            "classpath:db/book/delete-from-book-and-category-table.sql";
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        VALID_CATEGORY_1.setId(1L);
        VALID_CATEGORY_1.setName("Category 1");
        VALID_CATEGORY_1.setDescription("Category 1 description");

        VALID_CATEGORY_2.setId(2L);
        VALID_CATEGORY_2.setName("Category 2");
        VALID_CATEGORY_2.setDescription("Category 2 description");

        VALID_BOOK_DTO_1.setId(1L);
        VALID_BOOK_DTO_1.setTitle("Title 1");
        VALID_BOOK_DTO_1.setAuthor("Author 1");
        VALID_BOOK_DTO_1.setIsbn("978-0545010221");
        VALID_BOOK_DTO_1.setDescription("Some description");
        VALID_BOOK_DTO_1.setCoverImage("Image 1");
        VALID_BOOK_DTO_1.setCategories(Set.of(VALID_CATEGORY_1.getId(),
                VALID_CATEGORY_2.getId()));
        VALID_BOOK_DTO_1.setPrice(BigDecimal.TEN);

        VALID_BOOK_DTO_2.setId(2L);
        VALID_BOOK_DTO_2.setTitle("Title 2");
        VALID_BOOK_DTO_2.setAuthor("Author 2");
        VALID_BOOK_DTO_2.setIsbn("978-0735619678");
        VALID_BOOK_DTO_2.setDescription("Some description");
        VALID_BOOK_DTO_2.setCoverImage("Image 2");
        VALID_BOOK_DTO_2.setCategories(Set.of(VALID_CATEGORY_2.getId()));
        VALID_BOOK_DTO_2.setPrice(BigDecimal.ONE);

        REQUEST_DTO_1.setTitle(VALID_BOOK_DTO_1.getTitle());
        REQUEST_DTO_1.setAuthor(VALID_BOOK_DTO_1.getAuthor());
        REQUEST_DTO_1.setIsbn(VALID_BOOK_DTO_1.getIsbn());
        REQUEST_DTO_1.setDescription(VALID_BOOK_DTO_1.getDescription());
        REQUEST_DTO_1.setCoverImage(VALID_BOOK_DTO_1.getCoverImage());
        REQUEST_DTO_1.setCategoryIds(VALID_BOOK_DTO_1.getCategories());
        REQUEST_DTO_1.setPrice(VALID_BOOK_DTO_1.getPrice());

        REQUEST_DTO_2.setTitle(VALID_BOOK_DTO_2.getTitle());
        REQUEST_DTO_2.setAuthor(VALID_BOOK_DTO_2.getAuthor());
        REQUEST_DTO_2.setIsbn(VALID_BOOK_DTO_2.getIsbn());
        REQUEST_DTO_2.setDescription(VALID_BOOK_DTO_2.getDescription());
        REQUEST_DTO_2.setCoverImage(VALID_BOOK_DTO_2.getCoverImage());
        REQUEST_DTO_2.setCategoryIds(VALID_BOOK_DTO_2.getCategories());
        REQUEST_DTO_2.setPrice(VALID_BOOK_DTO_2.getPrice());
    }

    @Test
    @DisplayName("Test creating book with valid request dto")
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createBook_validRequestDto_returnBookDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(REQUEST_DTO_1);

        MvcResult result = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(VALID_BOOK_DTO_1, actual, "id"));
    }

    @Test
    @DisplayName("Test getting all book dto with valid request")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAll_validRequestDto_returnBookDtoList() throws Exception {
        List<BookDto> expected = List.of(VALID_BOOK_DTO_1, VALID_BOOK_DTO_2);

        MvcResult result = mockMvc.perform(get("/api/books?page=0&size=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(expected.size(), actual.size());
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id"));
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(1), actual.get(1), "id"));
    }

    @Test
    @DisplayName("Test getting book dto by valid id")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getBookById_validBookId_returnBookDto() throws Exception {
        Long expectedBookId = VALID_BOOK_DTO_1.getId();

        MvcResult result = mockMvc.perform(get("/api/books/" + expectedBookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(VALID_BOOK_DTO_1, actual));
    }

    @Test
    @DisplayName("Test searching books by filter")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void searchBooks_validRequestDto_returnBookDtoList() throws Exception {
        List<BookDto> expected = List.of(VALID_BOOK_DTO_1, VALID_BOOK_DTO_2);
        String jsonRequest = objectMapper.writeValueAsString(SEARCH_PARAMETERS);

        MvcResult result = mockMvc.perform(get("/api/books/search")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(expected.size(), actual.size());
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id"));
    }

    @Test
    @DisplayName("Test updating book by id")
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void update_validRequestDto_returnBookDto() throws Exception {
        Long expectedBookId = VALID_BOOK_DTO_1.getId();
        REQUEST_DTO_2.setIsbn(VALID_BOOK_DTO_1.getIsbn());
        String jsonRequest = objectMapper.writeValueAsString(REQUEST_DTO_2);

        MvcResult result = mockMvc.perform(put("/api/books/" + expectedBookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(VALID_BOOK_DTO_2, actual, "id", "isbn"));
    }

    @Test
    @DisplayName("Test deleting book by id")
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void delete_validBookId_successful() throws Exception {
        mockMvc.perform(
                        delete("/api/books/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}
