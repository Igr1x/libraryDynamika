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

    private String client;

    private String bookTitle;

    private LocalDate taken;

    private LocalDate returned;
}
