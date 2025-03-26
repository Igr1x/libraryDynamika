package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.varnavskii.librarydynamika.repository.BookLoanRepository;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.BookLoanService;
import ru.varnavskii.librarydynamika.service.BookService;
import ru.varnavskii.librarydynamika.service.ClientService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookService bookService;
    private final ClientService clientService;

    @Override
    @Transactional
    public BookLoanEntity takeBook(long clientId, long bookId) {
        ClientEntity client = clientService.getClientOrThrowException(clientId);
        BookEntity book = bookService.getBookOrThrowException(bookId);
        BookLoanEntity bookLoanEntity = BookLoanEntity.builder()
            .client(client)
            .book(book)
            .borrowedAt(LocalDate.now())
            .build();
        return bookLoanRepository.save(bookLoanEntity);
    }

    @Override
    @Transactional
    public void returnBooks(List<Long> ids) {
        bookLoanRepository.returnBooks(LocalDate.now(), ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookLoanEntity> getBookLoans(Specification<BookLoanEntity> specification, Pageable pageable, boolean returnedFilter) {
        return bookLoanRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookLoanEntity> getAllBookLoansRecords() {
        return bookLoanRepository.findAll();
    }
}
