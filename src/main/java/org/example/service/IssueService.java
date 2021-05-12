package org.example.service;

import org.example.entity.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue save(Issue issue);

    List<Issue> getAll();

    Optional<Issue> getById(Long id);
}
