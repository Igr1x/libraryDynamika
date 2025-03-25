package ru.varnavskii.librarydynamika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.varnavskii.librarydynamika.repository.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
