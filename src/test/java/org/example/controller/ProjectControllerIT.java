package org.example.controller;

import org.example.entity.Project;
import org.example.entity.User;
import org.example.repository.ProjectRepository;
import org.example.repository.UserRepository;
import org.example.utils.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class ProjectControllerIT extends BaseIT {

    private final String PROJECTS_REQUEST_URL = "/api/v1/projects";

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Sql(scripts = "project_getAll.sql")
    void getAll_test() throws Exception {
        //when
        ResultActions result = performGetRequest(PROJECTS_REQUEST_URL);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$..id", hasSize(2)));
    }

    @Test
    void create_test() throws Exception {
        //given
        Project project = new Project();

        project.setDescription("descr");

        project.setName("name");

        String json = objectMapper.writeValueAsString(List.of(project));

        //when
        ResultActions result = performPutRequest(PROJECTS_REQUEST_URL, json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @Test
    @Sql(scripts = "project_addUser.sql")
    void addUser_test() throws Exception {
        //given
        long userId = 1L;

        long projectId = 1L;

        String json = objectMapper.writeValueAsString(projectId);

        //when
        ResultActions result = performPostRequest(PROJECTS_REQUEST_URL + "/{id}/addUser", json, userId);

        //then
        result.andExpect(status().isOk());

        Project project = projectRepository.getOne(projectId);

        User u = userRepository.getOne(userId);

        assertThat(new ArrayList<>(u.getProjects()).get(0)).isEqualTo(project);
    }

    @Test
    @Sql(scripts = "project_removeUser.sql")
    void removeUser_test() throws Exception {
        //given
        long userId = 1L;

        long projectId = 1L;

        String json = objectMapper.writeValueAsString(projectId);

        //when
        ResultActions result = performPostRequest(PROJECTS_REQUEST_URL + "/{id}/removeUser", json, userId);

        //then
        result.andExpect(status().isOk());

        User user = userRepository.getOne(userId);

        assertTrue(user.getProjects().isEmpty());
    }
}