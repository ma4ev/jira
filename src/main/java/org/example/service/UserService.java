package org.example.service;

import org.example.entity.User;
import org.example.model.UserFilter;

import java.util.Collection;
import java.util.List;

public interface UserService {

    List<User> getAll();

    List<User> save(List<User> users);

    User save(User user);

    List<User> getByIds(Collection<Long> ids);

    User getById(Long id);

    List<User> get(UserFilter filter);
}
