package ru.varnavskii.librarydynamika.service;

import ru.varnavskii.librarydynamika.repository.entity.BookEntity;

public interface BookService {

    BookEntity createBook(BookEntity book);

    BookEntity getBookOrThrowException(long id);

    BookEntity updateBook(BookEntity book);

    void deleteBook(long id);
}
