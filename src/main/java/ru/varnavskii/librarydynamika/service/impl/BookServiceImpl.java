package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.varnavskii.librarydynamika.repository.BookRepository;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.service.BookService;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookEntity createBook(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public BookEntity getBookOrThrowException(long id) {
        return bookRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Book with id '%d' not found", id)));
    }

    @Override
    public BookEntity updateBook(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookEntity> getBooks(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        bookRepository.deleteAllById(ids);
    }
}
