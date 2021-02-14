package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateProjectRequest;
import org.example.dto.ProjectResponse;
import org.example.entity.Project;
import org.example.mapper.ProjectMapper;
import org.example.mapper.ProjectResponseMapper;
import org.example.service.ProjectService;
import org.example.service.UserManagementProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

    private final ProjectResponseMapper projectResponseMapper;

    private final ProjectMapper projectMapper;

    private final ProjectService projectService;

    private final UserManagementProjectService userManagementProjectService;

    @GetMapping
    public List<ProjectResponse> getAll() {
        return projectResponseMapper.toDomain(projectService.getAll());
    }

    @PutMapping
    public List<ProjectResponse> create(@Valid @RequestBody List<CreateProjectRequest> requests){
        List<Project> projects = projectMapper.toDomain(requests);

        return projectResponseMapper.toDomain(projectService.save(projects));
    }

    @PostMapping("/{id}/addUser")
    public void addUser(@PathVariable("id") Long projectId, @RequestBody @NotNull Long userId) {
        userManagementProjectService.addUser(projectId, userId);
    }

    @PostMapping("/{id}/removeUser")
    public void removeUser(@PathVariable("id") Long projectId, @RequestBody @NotNull Long userId) {
        userManagementProjectService.removeUser(projectId, userId);
    }
}
