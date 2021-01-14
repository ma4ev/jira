package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Project;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> save(List<Project> projects) {
        return projectRepository.saveAll(projects);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.getOne(id);
    }
}
