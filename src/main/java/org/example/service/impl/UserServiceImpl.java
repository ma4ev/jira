package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.model.UserFilter;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.service.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserSpecification userSpecification;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> save(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getByIds(Collection<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> get(UserFilter filter) {
        Specification<User> spec = userSpecification.get(filter);

        return userRepository.findAll(spec);
    }
}
