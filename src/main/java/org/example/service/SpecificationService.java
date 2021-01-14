package org.example.service;

import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface SpecificationService<T> {

    Specification<T> attributeContains(String attribute, String value);

    Specification<T> attributeExactly(String attribute, String value);

    Specification<T> attributeIn(String attribute, Long value);

    Specification<T> attributeIn(String joinField, String attribute, Long value);

    Specification<T> attributeIn(String attribute, Collection<Long> ids);
}
