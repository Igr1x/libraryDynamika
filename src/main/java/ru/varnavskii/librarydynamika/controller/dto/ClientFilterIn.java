package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientFilterIn {
    private String firstName;

    private String lastName;

    private String patronymic;

    private LocalDate birthDate;
}
