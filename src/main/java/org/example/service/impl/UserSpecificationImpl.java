package org.example.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.entity.Project_;
import org.example.entity.User;
import org.example.entity.User_;
import org.example.model.UserFilter;
import org.example.service.SpecificationService;
import org.example.service.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSpecificationImpl implements UserSpecification {

    private final SpecificationService<User> specificationService;

    @Override
    public Specification<User> get(UserFilter filter) {
        return (Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) ->
                Specification
                        .where(idsIn(filter.getIds()))
                        .and(firstNameContains(filter.getFirstName()))
                        .and(lastNameContains(filter.getLastName()))
                        .and(middleNameContains(filter.getMiddleName()))
                        .and(tagNameContains(filter.getTagName()))
                        .and(emailContains(filter.getEmail()))
                        .and(phoneContains(filter.getPhone()))
                        .and(telegramContains(filter.getTelegram()))
                        .and(byProjectId(filter.getProjectId()))
                        .toPredicate(root, cq, cb);
    }

    private Specification<User> idsIn(List<Long> ids){
        return specificationService.attributeIn(User_.ID, ids);
    }

    private Specification<User> firstNameContains(String value) {
        return specificationService.attributeContains(User_.FIRST_NAME, value);
    }

    private Specification<User> lastNameContains(String value) {
        return specificationService.attributeContains(User_.LAST_NAME, value);
    }

    private Specification<User> middleNameContains(String value) {
        return specificationService.attributeContains(User_.MIDDLE_NAME, value);
    }

    private Specification<User> tagNameContains(String value) {
        return specificationService.attributeContains(User_.TAG_NAME, value);
    }

    private Specification<User> emailContains(String value) {
        return specificationService.attributeContains(User_.EMAIL, value);
    }

    private Specification<User> phoneContains(String value) {
        return specificationService.attributeContains(User_.PHONE, value);
    }

    private Specification<User> telegramContains(String value) {
        return specificationService.attributeContains(User_.TELEGRAM, value);
    }

    private Specification<User> byProjectId(Long projectId){
        return specificationService.attributeIn(User_.PROJECTS, Project_.ID,  projectId);
    }
}
