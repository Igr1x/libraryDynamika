package ru.varnavskii.librarydynamika.service;

import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;

public interface BookLoanService {

    BookLoanEntity findBookLoanOrThrowException(long id);

    void takeBook(long userId, long bookId);

    void returnBook(long id);
}
