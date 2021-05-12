package org.example.mapper;

import org.example.entity.Issue;
import org.example.entity.Project;
import org.example.entity.User;
import org.example.exception.ProjectNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.example.transport.dto.CreateIssueRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class IssueMapper {

    protected ProjectService projectService;

    protected UserService userService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "project", expression = "java( getProject(request) )")
    @Mapping(target = "author", expression = "java( getAuthor(request) )")
    public abstract Issue toDomain(CreateIssueRequest request);

    protected Project getProject(CreateIssueRequest request) {
        return projectService
                .getById(request.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(request.getProjectId()));
    }

    protected User getAuthor(CreateIssueRequest request) {
        return userService
                .getById(request.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(request.getAuthorId()));
    }
}
