package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.varnavskii.librarydynamika.repository.BookLoanRepository;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.BookLoanService;
import ru.varnavskii.librarydynamika.service.BookService;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookService bookService;
    private final ClientService clientService;

    @Override
    @Transactional(readOnly = true)
    public BookLoanEntity findBookLoanOrThrowException(long id) {
        return bookLoanRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Book with id '%d' not found", id)));
    }

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
    public BookLoanEntity returnBook(long id) {
        BookLoanEntity existedBookLoan = findBookLoanOrThrowException(id);
        existedBookLoan.setReturnedAt(LocalDate.now());
        return bookLoanRepository.save(existedBookLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookLoanEntity> getBookLoans(Pageable pageable, boolean returnedFilter) {
        if (returnedFilter) {
            return bookLoanRepository.findAll(pageable);
        }
        return bookLoanRepository.findAllByReturnedAtIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookLoanEntity> getAllBookLoansRecords() {
        return bookLoanRepository.findAll();
    }
}
