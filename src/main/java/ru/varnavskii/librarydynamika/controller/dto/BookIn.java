package ru.varnavskii.librarydynamika.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class BookIn {

    private static final String ISBN_PATTERN = "^(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+$";

    public static final String TITLE_MESSAGE = "Title can't be null or empty";
    public static final String AUTHOR_MESSAGE = "Author name can't be null or empty";
    public static final String ISBN_MESSAGE = "ISBN can't be null or empty";
    public static final String ISBN_INCORRECT_FORMAT_MESSAGE = "ISBN format is incorrect";

    @NotNull(message = TITLE_MESSAGE)
    @NotEmpty(message = TITLE_MESSAGE)
    private String title;

    @NotNull(message = AUTHOR_MESSAGE)
    @NotEmpty(message = AUTHOR_MESSAGE)
    private String author;

    @NotNull(message = ISBN_MESSAGE)
    @NotEmpty(message = ISBN_MESSAGE)
    @Pattern(regexp = ISBN_PATTERN, message = ISBN_INCORRECT_FORMAT_MESSAGE)
    private String isbn;
}
