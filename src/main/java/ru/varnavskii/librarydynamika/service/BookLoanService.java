package ru.varnavskii.librarydynamika.service;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;

import java.util.List;

public interface BookLoanService {

    BookLoanEntity findBookLoanOrThrowException(long id);

    BookLoanEntity takeBook(long clientId, long bookId);

    BookLoanEntity returnBook(long id);

    Page<BookLoanEntity> getBookLoans(Pageable pageable, boolean returnedFilter);

    List<BookLoanEntity> getAllBookLoansRecords();
}
