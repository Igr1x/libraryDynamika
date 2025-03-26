package ru.varnavskii.librarydynamika.IT;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookLoanIT extends IntegrationTestBase {

    @Test
    @SneakyThrows
    public void testCreateClientBookRecordAndGetReport() {
        String clientFirstName = "testFirstName";
        String clientLastName = "testLastName";
        LocalDate clientBirthDate = LocalDate.now();
        String bookTitle = "testTitle";
        String authorName = "testAuthorName";
        String isbn = "123-123-1234";

        MvcResult resultClient = mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", clientFirstName)
                .param("lastName", clientLastName)
                .param("birthDate", clientBirthDate.toString()))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        String location = resultClient.getResponse().getHeader("Location");
        long clientId = Long.parseLong(location.replaceAll("\\/client\\/", ""));

        MvcResult resultBook = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", bookTitle)
                .param("author", authorName)
                .param("isbn", isbn))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        location = resultBook.getResponse().getHeader("Location");
        long bookId = Long.parseLong(location.replaceAll("\\/book\\/", ""));
        mockMvc.perform(post("/bookLoan/takeBook")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("clientId", String.valueOf(clientId))
                .param("bookId", String.valueOf(bookId)))
            .andReturn();

        mockMvc.perform(get("/bookLoan/report")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].book.id").value(bookId))
            .andExpect(jsonPath("$[0].client.id").value(clientId))
            .andExpect(jsonPath("$[0].book.title").value(bookTitle))
            .andExpect(jsonPath("$[0].client.firstName").value(clientFirstName));

    }

}
