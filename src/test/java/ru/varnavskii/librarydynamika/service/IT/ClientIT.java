package ru.varnavskii.librarydynamika.service.IT;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MvcResult;

import ru.varnavskii.librarydynamika.controller.dto.ClientIn;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        ClientIn clientIn = new ClientIn(clientFirstName, clientLastName, null, clientBirthDate);

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

    @Test
    @SneakyThrows
    public void testCreatClientWithIncorrectData() {
        ClientIn clientIn = new ClientIn(null, null, null, null);
        String response = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientIn)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Map<String, String> errors = objectMapper.readValue(response, Map.class);

        assertThat(errors).containsEntry("firstName", ClientIn.FIRST_NAME_MESSAGE);
        assertThat(errors).containsEntry("lastName", ClientIn.LAST_NAME_MESSAGE);
        assertThat(errors).containsEntry("birthDate", ClientIn.BIRTH_DATE_MESSAGE);
    }

}
