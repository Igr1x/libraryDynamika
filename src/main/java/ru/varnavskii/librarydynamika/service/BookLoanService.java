package ru.varnavskii.librarydynamika.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;

import java.util.List;

public interface BookLoanService {

    BookLoanEntity takeBook(long clientId, long bookId);

    void returnBooks(List<Long> ids);

    Page<BookLoanEntity> getBookLoans(Specification<BookLoanEntity> specification, Pageable pageable, boolean returnedFilter);

    List<BookLoanEntity> getAllBookLoansRecords();
}
