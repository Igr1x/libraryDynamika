package ru.varnavskii.librarydynamika.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book_loans")
public class BookLoanEntity {
    private static final String USER_ID_COLUMN = "user_id";
    private static final String BOOK_ID_COLUMN = "book_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN, nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = BOOK_ID_COLUMN, nullable = false)
    private BookEntity book;

    @Column(nullable = false)
    private LocalDate borrowedAt;

    private LocalDate returnedAt;
}
