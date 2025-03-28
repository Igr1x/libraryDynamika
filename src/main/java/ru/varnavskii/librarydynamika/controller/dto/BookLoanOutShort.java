package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoanOutShort {
    private long id;

    private String clientFirstName;

    private String clientLastName;

    private String clientPatronymic;

    private LocalDate clientBirthDate;

    private String bookTitle;

    private String bookAuthor;

    private String bookIsbn;

    private LocalDate taken;

    private LocalDate returned;
}
