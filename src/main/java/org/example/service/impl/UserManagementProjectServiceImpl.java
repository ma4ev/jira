package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Project;
import org.example.entity.User;
import org.example.exception.ProjectNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.service.ProjectService;
import org.example.service.UserManagementProjectService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementProjectServiceImpl implements UserManagementProjectService {

    private final UserService userService;

    private final ProjectService projectService;

    @Transactional
    @Override
    public void addUser(long projectId, long userId) {
        User user = getUser(userId);

        Project project = getProject(projectId);

        user.addProject(project);

        userService.save(user);
    }

    @Transactional
    @Override
    public void removeUser(long projectId, long userId) {
        User user = getUser(userId);

        Project project = getProject(projectId);

        user.removeProject(project);

        userService.save(user);
    }

    private User getUser(long userId){
        return Optional
                .ofNullable(userService.getById(userId))
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Project getProject(long projectId){
        return Optional
                .ofNullable(projectService.getById(projectId))
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }
}
