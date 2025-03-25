package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BookLoanIn {

    public static final String CLIENT_ID_MESSAGE = "Need to choose a client";
    public static final String BOOK_ID_MESSAGE = "Need to choose a book";

    @NotNull(message = CLIENT_ID_MESSAGE)
    private Long clientId;

    @NotNull(message = BOOK_ID_MESSAGE)
    private Long bookId;
}
