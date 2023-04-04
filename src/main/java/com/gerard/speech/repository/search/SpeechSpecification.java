package com.gerard.speech.repository.search;

import com.gerard.speech.model.domain.Speech;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.Objects;


@RequiredArgsConstructor
public class SpeechSpecification implements Specification<Speech> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Speech> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;
        switch (Objects.requireNonNull(searchCriteria.getOperation())) {
            case CONTAINS ->
                    predicate = criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
            case EQUAL ->
                    predicate = equalCriteria(root, criteriaBuilder);
            case GREATER_THAN_OR_EQUAL ->
                    predicate = greaterThanOrEqualToCriteria(root, criteriaBuilder);
            case LESS_THAN_OR_EQUAL ->
                    predicate =lessThanOrEqualToCriteria(root, criteriaBuilder);
        }

        return predicate;
    }

    private Predicate equalCriteria(Root<Speech> root, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getKey().equals("date")) {
            return criteriaBuilder.equal(root.get(searchCriteria.getKey()), LocalDate.parse(searchCriteria.getValue()));
        } else {
            return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
        }
    }

    private Predicate greaterThanOrEqualToCriteria(Root<Speech> root, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getKey().equals("date")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), LocalDate.parse(searchCriteria.getValue()));
        } else {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue());
        }
    }

    private Predicate lessThanOrEqualToCriteria(Root<Speech> root, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getKey().equals("date")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), LocalDate.parse(searchCriteria.getValue()));
        } else {
            return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue());
        }
    }
}
