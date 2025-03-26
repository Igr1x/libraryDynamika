package ru.varnavskii.librarydynamika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoanEntity, Long>, JpaSpecificationExecutor<BookLoanEntity> {
    @Modifying
    @Query("UPDATE BookLoanEntity b SET b.returnedAt = :returnedAt WHERE b.id IN :ids")
    void returnBooks(@Param("returnedAt") LocalDate returnedAt, @Param("ids") List<Long> ids);
}
