package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookLoanFilterIn {
    private String clientFirstName;

    private String clientLastName;

    private String clientPatronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate clientBirthDate;

    private String bookTitle;

    private String bookAuthor;

    private String bookIsbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate takenDate;

    private String showClosedRecords;
}
