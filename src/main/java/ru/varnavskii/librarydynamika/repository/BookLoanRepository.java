package ru.varnavskii.librarydynamika.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoanEntity, Long> {
    Page<BookLoanEntity> findAllByReturnedAtIsNull(Pageable page);
}
