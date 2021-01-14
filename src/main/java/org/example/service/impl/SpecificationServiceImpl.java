package org.example.service.impl;

import org.example.entity.Project_;
import org.example.entity.User_;
import org.example.service.SpecificationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service
public class SpecificationServiceImpl<T> implements SpecificationService<T> {

    public Specification<T> attributeContains(String attribute, String value) {
        return Objects.isNull(value) ? null : attributeExactly(attribute, "%" + value + "%");
    }

    public Specification<T> attributeExactly(String attribute, String value) {
        return (root, cq, cb) -> {
            if (Objects.isNull(value)) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    value.toLowerCase()
            );
        };
    }

    @Override
    public Specification<T> attributeIn(String attribute, Long value) {
        return (root, cq, cb) -> {
            if (Objects.isNull(value)) {
                return null;
            }

            return cb.in(root.get(attribute)).in(Collections.singleton(value));
        };
    }

    @Override
    public Specification<T> attributeIn(String joinField, String attribute, Long value) {
        return (root, cq, cb) -> {
            if (Objects.isNull(value)) {
                return null;
            }

           return root.join(User_.PROJECTS).get(Project_.ID).in(value);
        };
    }

    @Override
    public Specification<T> attributeIn(String attribute, Collection<Long> ids) {
        return (root, cq, cb) -> {
            if (Objects.isNull(ids) || ids.isEmpty()) {
                return null;
            }

            return cb.in(root.get(attribute)).value(ids);
        };
    }
}
