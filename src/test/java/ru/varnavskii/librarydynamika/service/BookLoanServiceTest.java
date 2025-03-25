package ru.varnavskii.librarydynamika.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.varnavskii.librarydynamika.repository.BookLoanRepository;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.UserEntity;
import ru.varnavskii.librarydynamika.service.impl.BookLoanServiceImpl;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookLoanServiceTest {
    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookLoanServiceImpl bookLoanService;

    private final static Long USER_UD = 1L;
    private final static Long BOOK_ID = 1L;
    private final static Long BOOK_LOAN_ID = 1L;

    private UserEntity user;
    private BookEntity book;
    private BookLoanEntity bookLoanEntity;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
            .id(USER_UD)
            .birthDate(LocalDate.of(2003, 1, 5))
            .firstName("SomeFirstName")
            .lastName("SomeLastName")
            .build();

        book = BookEntity.builder()
            .id(BOOK_ID)
            .author("SomeAuthor")
            .isbn("ISBN-123")
            .build();

        bookLoanEntity = BookLoanEntity.builder()
            .id(BOOK_LOAN_ID)
            .user(user)
            .book(book)
            .borrowedAt(LocalDate.now())
            .build();
    }

    @Test
    void testTakeBookSuccess() {
        when(userService.getUserOrThrowException(USER_UD)).thenReturn(user);
        when(bookService.getBookOrThrowException(BOOK_ID)).thenReturn(book);
        when(bookLoanRepository.save(Mockito.any(BookLoanEntity.class))).thenReturn(bookLoanEntity);

        BookLoanEntity result = bookLoanService.takeBook(USER_UD, BOOK_ID);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(book, result.getBook());
        assertNotNull(result.getBorrowedAt());
        verify(bookLoanRepository, times(1)).save(Mockito.any(BookLoanEntity.class));
    }

    @Test
    void testTakeBookUserNotFound() {
        when(userService.getUserOrThrowException(USER_UD)).thenThrow(new EntityNotFoundException("User not found"));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookLoanService.takeBook(USER_UD, BOOK_ID);
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testTakeBookBookNotFound() {
        when(userService.getUserOrThrowException(USER_UD)).thenReturn(user);
        when(bookService.getBookOrThrowException(BOOK_ID)).thenThrow(new EntityNotFoundException("Book not found"));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookLoanService.takeBook(USER_UD, BOOK_ID);
        });

        assertTrue(exception.getMessage().contains("Book not found"));
    }

    @Test
    void testReturnBookSuccess() {
        when(bookLoanRepository.findById(BOOK_LOAN_ID)).thenReturn(Optional.of(bookLoanEntity));
        when(bookLoanRepository.save(Mockito.any(BookLoanEntity.class))).thenReturn(bookLoanEntity);

        BookLoanEntity result = bookLoanService.returnBook(BOOK_LOAN_ID);

        assertNotNull(result);
        assertNotNull(result.getReturnedAt());
        verify(bookLoanRepository, times(1)).save(Mockito.any(BookLoanEntity.class));
    }

    @Test
    void testReturnBookNotFound() {
        when(bookLoanRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookLoanService.returnBook(BOOK_LOAN_ID);
        });

        assertTrue(exception.getMessage().contains("Book with id '1' not found"));
    }
}
