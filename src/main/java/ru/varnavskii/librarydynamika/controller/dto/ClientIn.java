package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientIn {

    @NotNull(message = "")
    private String firstName;

    @NotNull(message = "")
    private String lastName;

    private String patronymic;

    @NotNull(message = "")
    private LocalDate birthDate;
}
