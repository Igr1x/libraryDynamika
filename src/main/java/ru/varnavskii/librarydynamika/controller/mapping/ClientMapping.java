package ru.varnavskii.librarydynamika.controller.mapping;

import org.springframework.stereotype.Component;

import ru.varnavskii.librarydynamika.controller.dto.ClientIn;
import ru.varnavskii.librarydynamika.controller.dto.ClientOut;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

@Component
public class ClientMapping {

    public ClientEntity toEntity(ClientIn clientIn) {
        return ClientEntity.builder()
            .firstName(clientIn.getFirstName())
            .lastName(clientIn.getLastName())
            .patronymic(clientIn.getPatronymic())
            .birthDate(clientIn.getBirthDate())
            .build();
    }

    public ClientOut toOut(ClientEntity clientEntity) {
        return ClientOut.builder()
            .id(clientEntity.getId())
            .firstName(clientEntity.getFirstName())
            .lastName(clientEntity.getLastName())
            .patronymic(clientEntity.getPatronymic())
            .birthDate(clientEntity.getBirthDate())
            .build();
    }
}
