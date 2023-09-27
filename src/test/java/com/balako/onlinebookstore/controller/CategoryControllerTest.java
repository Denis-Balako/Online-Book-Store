package com.balako.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.balako.onlinebookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.balako.onlinebookstore.dto.category.request.CreateCategoryRequestDto;
import com.balako.onlinebookstore.dto.category.response.CategoryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
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
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static final CategoryDto VALID_CATEGORY_DTO_1 = new CategoryDto();
    private static final CategoryDto VALID_CATEGORY_DTO_2 = new CategoryDto();
    private static final CreateCategoryRequestDto REQUEST_DTO =
            new CreateCategoryRequestDto();
    private static final BookDtoWithoutCategoryIds DTO_WITHOUT_CATEGORY_IDS =
            new BookDtoWithoutCategoryIds();
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

        VALID_CATEGORY_DTO_1.setId(1L);
        VALID_CATEGORY_DTO_1.setName("Category 1");
        VALID_CATEGORY_DTO_1.setDescription("Category 1 description");

        VALID_CATEGORY_DTO_2.setId(2L);
        VALID_CATEGORY_DTO_2.setName("Category 2");
        VALID_CATEGORY_DTO_2.setDescription("Category 2 description");

        REQUEST_DTO.setName(VALID_CATEGORY_DTO_1.getName());
        REQUEST_DTO.setDescription(VALID_CATEGORY_DTO_1.getDescription());

        DTO_WITHOUT_CATEGORY_IDS.setId(1L);
        DTO_WITHOUT_CATEGORY_IDS.setTitle("Title 1");
        DTO_WITHOUT_CATEGORY_IDS.setAuthor("Author 1");
        DTO_WITHOUT_CATEGORY_IDS.setIsbn("978-0545010221");
        DTO_WITHOUT_CATEGORY_IDS.setDescription("Some description");
        DTO_WITHOUT_CATEGORY_IDS.setCoverImage("Image 1");
        DTO_WITHOUT_CATEGORY_IDS.setPrice(BigDecimal.TEN);
    }

    @Test
    @DisplayName("Test creating book dto with valid request dto")
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategory_ValidRequest_returnCategoryDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(REQUEST_DTO);

        MvcResult result = mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(VALID_CATEGORY_DTO_1, actual, "id"));
    }

    @Test
    @DisplayName("Test getting all categories dto with valid request")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAll_validRequestDto_returnCategoryDtoList() throws Exception {
        List<CategoryDto> expected = List.of(VALID_CATEGORY_DTO_1, VALID_CATEGORY_DTO_2);

        MvcResult result = mockMvc.perform(get("/api/categories?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(expected.size(), actual.size());
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id"));
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(1), actual.get(1), "id"));
    }

    @Test
    @DisplayName("Test getting category dto by valid id")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCategoryById_validCategoryId_returnCategoryDto() throws Exception {
        Long expectedCategoryId = VALID_CATEGORY_DTO_1.getId();

        MvcResult result = mockMvc.perform(get("/api/categories/"
                        + expectedCategoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(VALID_CATEGORY_DTO_1, actual));
    }

    @Test
    @DisplayName("Test updating category by id")
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCategory_validRequestDto_returnBookDto() throws Exception {
        Long expectedCategoryId = VALID_CATEGORY_DTO_2.getId();
        String jsonRequest = objectMapper.writeValueAsString(REQUEST_DTO);

        MvcResult result = mockMvc.perform(put("/api/categories/"
                        + expectedCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(VALID_CATEGORY_DTO_1, actual, "id"));
    }

    @Test
    @DisplayName("Test deleting category by id")
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCategory_validCategoryId_successful() throws Exception {
        mockMvc.perform(
                        delete("/api/categories/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test getting books dto without categories by category id")
    @WithMockUser(username = USER_USERNAME)
    @Sql(scripts = SQL_SCRIPT_BEFORE_METHOD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SQL_SCRIPT_AFTER_METHOD,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getBooksByCategoryId_validRequestDto_returnBookDtoWithoutCategoryIdsList()
            throws Exception {
        List<BookDtoWithoutCategoryIds> expected = List.of(DTO_WITHOUT_CATEGORY_IDS);
        Long categoryId = VALID_CATEGORY_DTO_1.getId();

        MvcResult result = mockMvc.perform(get("/api/categories/" + categoryId
                        + "/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDtoWithoutCategoryIds> actual = objectMapper.readValue(result.getResponse()
                        .getContentAsString(),
                new TypeReference<>() {});

        assertEquals(expected.size(), actual.size());
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id"));
    }
}
