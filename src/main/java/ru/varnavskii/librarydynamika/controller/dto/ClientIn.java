package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientIn {

    public static final String FIRST_NAME_MESSAGE = "First name can't be null or empty";
    public static final String LAST_NAME_MESSAGE = "Last name can't be null or empty";
    public static final String BIRTH_DATE_MESSAGE = "Birth date can't be null";
    public static final String INCORRECT_BIRTH_DATE = "Birth date must be less than the current date";

    @NotNull(message = FIRST_NAME_MESSAGE)
    @NotEmpty(message = FIRST_NAME_MESSAGE)
    private String firstName;

    @NotNull(message = LAST_NAME_MESSAGE)
    @NotEmpty(message = LAST_NAME_MESSAGE)
    private String lastName;

    private String patronymic;

    @NotNull(message = BIRTH_DATE_MESSAGE)
    @PastOrPresent(message = INCORRECT_BIRTH_DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
