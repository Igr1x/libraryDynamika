package ru.varnavskii.librarydynamika.controller.mapping;

import org.springframework.stereotype.Component;

import ru.varnavskii.librarydynamika.controller.dto.BookIn;
import ru.varnavskii.librarydynamika.controller.dto.BookOut;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;

@Component
public class BookMapper {

    public BookEntity toEntity(BookIn bookIn) {
        return BookEntity.builder()
            .title(bookIn.getTitle())
            .author(bookIn.getAuthor())
            .isbn(bookIn.getIsbn())
            .build();
    }

    public BookOut toOut(BookEntity bookEntity) {
        return BookOut.builder()
            .id(bookEntity.getId())
            .title(bookEntity.getTitle())
            .author(bookEntity.getAuthor())
            .isbn(String.format("ISBN-%s", bookEntity.getIsbn()))
            .build();
    }
}
