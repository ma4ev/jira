package org.example.service;

import org.example.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getAll();

    List<Project> save(List<Project> projects);

    Project save(Project project);

    Optional<Project> getById(Long id);
}
