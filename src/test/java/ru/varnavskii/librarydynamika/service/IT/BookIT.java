package ru.varnavskii.librarydynamika.service.IT;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookIT extends IntegrationTestBase {

    private static final String URL = "/book";

    @Test
    @SneakyThrows
    void testCRUDBook() {
        String bookTitle = "testTitle";
        String authorName = "testAuthorName";
        String isbn = "123-123-1234";

        MvcResult result = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", bookTitle)
                .param("author", authorName)
                .param("isbn", isbn))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        String location = result.getResponse().getHeader("Location");
        long bookId = Long.parseLong(location.replaceAll("\\/book\\/", ""));

        mockMvc.perform(get(URL + "/" + bookId))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("book"))
            .andExpect(model().attribute("book", hasProperty("id", is(bookId))))
            .andExpect(model().attribute("book", hasProperty("title", is(bookTitle))))
            .andExpect(model().attribute("book", hasProperty("author", is(authorName))))
            .andExpect(model().attribute("book", hasProperty("isbn", is("ISBN-" + isbn))));
    }
}
