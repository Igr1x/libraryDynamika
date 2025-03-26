package ru.varnavskii.librarydynamika.common.utils;

import org.springframework.data.jpa.domain.Specification;

import ru.varnavskii.librarydynamika.controller.dto.ClientFilterIn;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ClientFilterApplier {
    public static Specification<ClientEntity> withFilters(ClientFilterIn filter) {
        return (Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("firstName"), "%" + filter.getFirstName() + "%"));
            }
            if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("lastName"), "%" + filter.getLastName() + "%"));
            }
            if (filter.getPatronymic() != null && !filter.getPatronymic().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("patronymic"), "%" + filter.getPatronymic() + "%"));
            }
            if (filter.getBirthDate() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("birthDate"), filter.getBirthDate()));
            }

            return predicate;
        };
    }
}
