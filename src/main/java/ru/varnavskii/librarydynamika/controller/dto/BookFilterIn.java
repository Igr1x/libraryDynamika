package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookFilterIn {
    private String title;

    private String author;

    private String isbn;
}
