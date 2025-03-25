package ru.varnavskii.librarydynamika.controller.mapping;

import org.springframework.stereotype.Component;

import ru.varnavskii.librarydynamika.controller.dto.BookLoanOutShort;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

@Component
public class BookLoanMapper {

    public BookLoanOutShort toOutShort(BookLoanEntity bookLoanEntity) {
        ClientEntity client = bookLoanEntity.getClient();
        return BookLoanOutShort.builder()
            .id(bookLoanEntity.getId())
            .client(client.getFirstName() + client.getLastName())
            .bookTitle(bookLoanEntity.getBook().getTitle())
            .taken(bookLoanEntity.getBorrowedAt())
            .returned(bookLoanEntity.getReturnedAt())
            .build();
    }
}
