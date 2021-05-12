package org.example.service.impl;

import org.example.entity.Project;
import org.example.entity.User;
import org.example.exception.ProjectNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectManagementServiceImplTest {

    @Mock
    UserService userService;

    @Mock
    ProjectService projectService;

    @InjectMocks
    ProjectManagementServiceImpl projectManagementService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @DisplayName("addUser - when user and project is exists")
    @Test
    void addUser_whenUserAndProjectIsExists() {
        //given
        long projectId = 1L;

        long userId = 1L;

        Project expectedProject = new Project();

        given(userService.getById(userId))
                .willReturn(Optional.of(new User()));

        given(projectService.getById(projectId))
                .willReturn(Optional.of(expectedProject));

        //when
        projectManagementService.addUser(projectId, userId);

        //then
        verify(userService).save(userArgumentCaptor.capture());

        Project actual = new ArrayList<>(userArgumentCaptor.getValue().getProjects()).get(0);

        assertThat(actual)
                .isEqualTo(expectedProject);
    }

    @DisplayName("removeUser - when user and project is exists")
    @Test
    void removeUser_whenUserAndProjectIsExists() {
        //given
        long projectId = 1L;

        long userId = 1L;

        Project expectedProject = new Project();

        User user = new User();

        user.addProject(expectedProject);

        given(userService.getById(userId))
                .willReturn(Optional.of(user));

        given(projectService.getById(projectId))
                .willReturn(Optional.of(expectedProject));

        //when
        projectManagementService.removeUser(projectId, userId);

        //then
        verify(userService).save(userArgumentCaptor.capture());

        Collection<Project> actual = userArgumentCaptor.getValue().getProjects();

        assertThat(actual).isEmpty();
    }

    @DisplayName("when user exists and project is not exists")
    @Test
    void whenUserExistsAndProjectIsNotExists() {
        //given
        long projectId = 1L;

        long userId = 1L;

        given(userService.getById(userId))
                .willReturn(Optional.of(new User()));

        //when
        ProjectNotFoundException e = assertThrows(
                ProjectNotFoundException.class,
                () -> projectManagementService.addUser(projectId, userId)
        );

        //then
        verify(userService, never()).save(any(User.class));
        assertThat(e.getMessage()).isEqualTo("Project with id 1 not found");
    }

    @DisplayName("when user is not exists and project is exists")
    @Test
    void whenUserNotExistsAndProjectIsExists() {
        //given
        long projectId = 1L;

        long userId = 1L;

        //when
        UserNotFoundException e = assertThrows(
                UserNotFoundException.class,
                () -> projectManagementService.addUser(projectId, userId)
        );

        //then
        verify(userService, never()).save(any(User.class));
        assertThat(e.getMessage()).isEqualTo("User with id 1 not found");
    }
}