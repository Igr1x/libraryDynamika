package ru.varnavskii.librarydynamika.service.IT;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import ru.varnavskii.librarydynamika.controller.dto.ClientIn;
import ru.varnavskii.librarydynamika.controller.dto.ClientOut;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

        String response = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientIn)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        ClientOut client = objectMapper.readValue(response, ClientOut.class);

        Assertions.assertNotNull(client);
        assertEquals(clientFirstName, client.getFirstName());
        assertEquals(clientLastName, client.getLastName());
        Assertions.assertNull(client.getPatronymic());
        assertEquals(clientBirthDate, client.getBirthDate());

        String newClientPatronymic = "patronymic";
        clientIn = new ClientIn(clientFirstName, clientLastName, newClientPatronymic, clientBirthDate);
        response = mockMvc.perform(put(URL + "/" + client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientIn)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        client = objectMapper.readValue(response, ClientOut.class);

        assertEquals(newClientPatronymic, client.getPatronymic());

        response = mockMvc.perform(delete(URL + "/" + client.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        assertTrue(response.contains(String.format("Client with id %d deleted", client.getId())));
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
