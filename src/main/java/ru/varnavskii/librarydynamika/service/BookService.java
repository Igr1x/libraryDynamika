package ru.varnavskii.librarydynamika.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ru.varnavskii.librarydynamika.repository.entity.BookEntity;

import java.util.List;

public interface BookService {

    BookEntity createBook(BookEntity book);

    BookEntity getBookOrThrowException(long id);

    BookEntity updateBook(BookEntity book);

    void deleteBook(long id);

    Page<BookEntity> getBooks(PageRequest pageRequest);

    void deleteByIds(List<Long> ids);
}
