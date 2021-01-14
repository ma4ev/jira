package org.example.service;

import org.example.entity.User;
import org.example.model.UserFilter;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpecification {

    Specification<User> get(UserFilter filter);
}
