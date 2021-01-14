package org.example.service;

import org.example.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    List<Project> save(List<Project> projects);

    Project save(Project project);

    Project getById(Long id);
}
