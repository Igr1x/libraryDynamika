package ru.varnavskii.librarydynamika.common.utils;

import org.springframework.data.jpa.domain.Specification;

import ru.varnavskii.librarydynamika.controller.dto.BookLoanFilterIn;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookLoanFilterApplier {
    public static Specification<BookLoanEntity> withFilters(BookLoanFilterIn filter) {
        return (Root<BookLoanEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            Join<BookLoanEntity, ClientEntity> clientJoin = root.join("client", JoinType.INNER);
            if (filter.getClientFirstName() != null && !filter.getClientFirstName().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(clientJoin.get("firstName"), "%" + filter.getClientFirstName() + "%"));
            }
            if (filter.getClientLastName() != null && !filter.getClientLastName().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(clientJoin.get("lastName"), "%" + filter.getClientLastName() + "%"));
            }
            if (filter.getClientPatronymic() != null && !filter.getClientPatronymic().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(clientJoin.get("patronymic"), "%" + filter.getClientPatronymic() + "%"));
            }
            if (filter.getClientBirthDate() != null) {
                predicate = cb.and(predicate, cb.equal(clientJoin.get("birthDate"), filter.getClientBirthDate()));
            }

            Join<BookLoanEntity, BookEntity> bookJoin = root.join("book", JoinType.INNER);
            if (filter.getBookTitle() != null && !filter.getBookTitle().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(bookJoin.get("title"), "%" + filter.getBookTitle() + "%"));
            }
            if (filter.getTakenDate() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("borrowedAt"), filter.getTakenDate()));
            }
            if (filter.getShowClosedRecords() == null) {
                predicate = cb.and(predicate, cb.isNull(root.get("returnedAt")));
            }

            return predicate;
        };
    }

}
