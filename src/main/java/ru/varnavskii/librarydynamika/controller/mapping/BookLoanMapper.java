package ru.varnavskii.librarydynamika.controller.mapping;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import ru.varnavskii.librarydynamika.controller.dto.BookLoanOut;
import ru.varnavskii.librarydynamika.controller.dto.BookLoanOutShort;
import ru.varnavskii.librarydynamika.controller.dto.BookOut;
import ru.varnavskii.librarydynamika.controller.dto.ClientOut;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

@Component
@RequiredArgsConstructor
public class BookLoanMapper {

    private final ClientMapper clientMapper;
    private final BookMapper bookMapper;

    public BookLoanOutShort toOutShort(BookLoanEntity bookLoanEntity) {
        ClientEntity client = bookLoanEntity.getClient();
        BookEntity book = bookLoanEntity.getBook();
        return BookLoanOutShort.builder()
            .id(bookLoanEntity.getId())
            .clientFirstName(client.getFirstName())
            .clientLastName(client.getLastName())
            .clientPatronymic(client.getPatronymic())
            .clientBirthDate(client.getBirthDate())
            .bookTitle(book.getTitle())
            .bookAuthor(book.getAuthor())
            .bookIsbn(book.getIsbn())
            .taken(bookLoanEntity.getBorrowedAt())
            .returned(bookLoanEntity.getReturnedAt())
            .build();
    }

    public BookLoanOut toOut(BookLoanEntity bookLoanEntity) {
        ClientOut client = clientMapper.toOut(bookLoanEntity.getClient());
        BookOut book = bookMapper.toOut(bookLoanEntity.getBook());
        return BookLoanOut.builder()
            .id(bookLoanEntity.getId())
            .client(client)
            .book(book)
            .taken(bookLoanEntity.getBorrowedAt())
            .returned(bookLoanEntity.getReturnedAt())
            .build();
    }
}
