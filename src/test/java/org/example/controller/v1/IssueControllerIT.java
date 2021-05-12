package org.example.controller.v1;

import org.example.mapper.IssueMapper;
import org.example.mapper.IssueResponseMapper;
import org.example.model.issue.IssuePriority;
import org.example.model.issue.IssueStatus;
import org.example.model.issue.IssueType;
import org.example.service.IssueService;
import org.example.transport.dto.CreateIssueRequest;
import org.example.utils.BaseIT;
import org.example.utils.DataProviderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class IssueControllerIT extends BaseIT {

    String ISSUE_REQUEST_URL = "/api/v1/issues";

    @Autowired
    IssueService issueService;

    @Autowired
    IssueMapper issueMapper;

    @Autowired
    IssueResponseMapper issueResponseMapper;

    @Test
    @Sql("project_addUser.sql")
    void create_success() throws Exception {
        //given
        var json = objectMapper.writeValueAsString(getCreateIssueRequest());

        //when
        var result = performPostRequest(ISSUE_REQUEST_URL, json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.issueId", is(1)));
    }

    @Test
    @Sql("project_addUser.sql")
    @Sql("issue_add.sql")
    void getAll() throws Exception {
        //when
        var result = performGetRequest(ISSUE_REQUEST_URL);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].issueId", is(1)))
                .andExpect(jsonPath("$..issueId", hasSize(1)));
    }

    @Test
    @Sql("project_addUser.sql")
    @Sql("issue_add.sql")
    void getById() throws Exception {
        //when
        var result = performGetRequest(ISSUE_REQUEST_URL + "/{id}", 1L);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.issueId", is(1)));
    }

    @Test
    void getById_issueNotFound() throws Exception {
        //when
       var result = performGetRequest(ISSUE_REQUEST_URL + "/{id}", 1L);

       //then
        var actualMsg = result.andReturn().getResponse().getContentAsString();

        assertAll(
                () -> result.andExpect(status().isNotFound()),
                () -> assertThat(actualMsg).isEqualToIgnoringCase("{\"message\":\"Issue with id 1 not found\"}")
        );
    }

    private CreateIssueRequest getCreateIssueRequest(){
        return CreateIssueRequest.builder()
                .projectId(1L)
                .authorId(1L)
                .component(RandomStringUtils.randomAlphabetic(1))
                .type(DataProviderUtils.randomEnum(IssueType.class))
                .theme(RandomStringUtils.randomAlphabetic(1))
                .priority(DataProviderUtils.randomEnum(IssuePriority.class))
                .status(DataProviderUtils.randomEnum(IssueStatus.class))
                .description(RandomStringUtils.randomAlphabetic(1))
                .initialAssessment(3L)
                .remainingTime(4L)
                .datePerformance(LocalDate.of(2021, 12, 31))
                .build();
    }
}
