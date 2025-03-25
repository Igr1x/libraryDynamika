package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.varnavskii.librarydynamika.repository.BookLoanRepository;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.UserEntity;
import ru.varnavskii.librarydynamika.service.BookLoanService;
import ru.varnavskii.librarydynamika.service.BookService;
import ru.varnavskii.librarydynamika.service.UserService;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookService bookService;
    private final UserService userService;

    @Override
    public BookLoanEntity findBookLoanOrThrowException(long id) {
        return bookLoanRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Book with id '%d' not found", id)));
    }

    @Override
    public BookLoanEntity takeBook(long userId, long bookId) {
        UserEntity user = userService.getUserOrThrowException(userId);
        BookEntity book = bookService.getBookOrThrowException(bookId);
        BookLoanEntity bookLoanEntity = BookLoanEntity.builder()
            .user(user)
            .book(book)
            .borrowedAt(LocalDate.now())
            .build();
        return bookLoanRepository.save(bookLoanEntity);
    }

    @Override
    public BookLoanEntity returnBook(long id) {
        BookLoanEntity existedBookLoan = findBookLoanOrThrowException(id);
        existedBookLoan.setReturnedAt(LocalDate.now());
        return bookLoanRepository.save(existedBookLoan);
    }
}
