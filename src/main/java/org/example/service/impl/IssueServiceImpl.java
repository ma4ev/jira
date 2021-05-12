package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Issue;
import org.example.repository.IssueRepository;
import org.example.service.IssueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository repository;

    @Override
    public Issue save(Issue issue) {
        return repository.save(issue);
    }

    @Override
    public List<Issue> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Issue> getById(Long id) {
        return repository.findById(id);
    }
}
