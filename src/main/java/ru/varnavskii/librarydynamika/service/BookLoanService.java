package ru.varnavskii.librarydynamika.service;

import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;

public interface BookLoanService {

    BookLoanEntity findBookLoanOrThrowException(long id);

    BookLoanEntity takeBook(long userId, long bookId);

    BookLoanEntity returnBook(long id);
}
