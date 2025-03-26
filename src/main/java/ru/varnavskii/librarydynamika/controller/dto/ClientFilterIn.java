package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientFilterIn {
    private String firstName;

    private String lastName;

    private String patronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
