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
public class BookLoanOut {
    private long id;

    private ClientOut client;

    private BookOut book;

    private LocalDate taken;

    private LocalDate returned;
}
