package ru.varnavskii.librarydynamika.IT;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientIT extends IntegrationTestBase {

    private static final String URL = "/client";

    @Test
    @SneakyThrows
    void testCRUDClient() {
        String clientFirstName = "testFirstName";
        String clientLastName = "testLastName";
        LocalDate clientBirthDate = LocalDate.now();

        MvcResult result = mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", clientFirstName)
                .param("lastName", clientLastName)
                .param("birthDate", clientBirthDate.toString()))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        String location = result.getResponse().getHeader("Location");
        long clientId = Long.parseLong(location.replaceAll("\\/client\\/", ""));

        mockMvc.perform(get("/client/" + clientId))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("client"))
            .andExpect(model().attribute("client", hasProperty("id", is(clientId))))
            .andExpect(model().attribute("client", hasProperty("firstName", is(clientFirstName))))
            .andExpect(model().attribute("client", hasProperty("lastName", is(clientLastName))))
            .andExpect(model().attribute("client", hasProperty("birthDate", is(clientBirthDate))));
    }

}
