package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public BookEntity createBook(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookEntity getBookOrThrowException(long id) {
        return bookRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Book with id '%d' not found", id)));
    }

    @Override
    @Transactional
    public BookEntity updateBook(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        bookRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookEntity> getBooks(Specification<BookEntity> specification, PageRequest pageRequest) {
        return bookRepository.findAll(specification, pageRequest);
    }
}
