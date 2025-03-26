package ru.varnavskii.librarydynamika.common.utils;

import org.springframework.data.jpa.domain.Specification;

import ru.varnavskii.librarydynamika.controller.dto.BookFilterIn;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookFilterApplier {
    public static Specification<BookEntity> withFilters(BookFilterIn filter) {
        return (Root<BookEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            if (filter.getAuthor() != null && !filter.getAuthor().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("author"), "%" + filter.getAuthor() + "%"));
            }
            if (filter.getIsbn() != null && !filter.getIsbn().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("isbn"), "%" + filter.getIsbn() + "%"));
            }

            return predicate;
        };
    }
}
