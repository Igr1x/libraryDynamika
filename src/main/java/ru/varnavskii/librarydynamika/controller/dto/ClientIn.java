package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientIn {

    public static final String FIRST_NAME_MESSAGE = "First name can't be null or empty";
    public static final String LAST_NAME_MESSAGE = "Last name can't be null or empty";
    public static final String BIRTH_DATE_MESSAGE = "Birth date can't be null";

    @NotNull(message = FIRST_NAME_MESSAGE)
    @NotEmpty(message = FIRST_NAME_MESSAGE)
    private String firstName;

    @NotNull(message = LAST_NAME_MESSAGE)
    @NotEmpty(message = LAST_NAME_MESSAGE)
    private String lastName;

    private String patronymic;

    @NotNull(message = BIRTH_DATE_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
