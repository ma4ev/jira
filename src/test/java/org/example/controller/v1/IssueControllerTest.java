package org.example.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.controlleradvice.ControllerExceptionHandler;
import org.example.mapper.IssueMapper;
import org.example.mapper.IssueResponseMapper;
import org.example.model.issue.IssuePriority;
import org.example.model.issue.IssueStatus;
import org.example.model.issue.IssueType;
import org.example.service.IssueService;
import org.example.transport.dto.CreateIssueRequest;
import org.example.utils.DataProviderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IssueController.class)
class IssueControllerTest {

    @MockBean
    IssueService issueService;

    @MockBean
    IssueMapper issueMapper;

    @MockBean
    IssueResponseMapper issueResponseMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CreateIssueRequest request;

    @Captor
    ArgumentCaptor<CreateIssueRequest> requestArgumentCaptor;

    @BeforeEach
    void setUp() {
        request = CreateIssueRequest.builder()
                .projectId(1L)
                .authorId(2L)
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

    @Test
    void create_success() throws Exception {
        //when
        ResultActions result = mockMvc.perform(
                post("/api/v1/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        assertAll(
                () -> result.andExpect(status().isOk()),
                () -> verify(issueMapper).toDomain(requestArgumentCaptor.capture()),
                () -> {
                    var actualRequest = requestArgumentCaptor.getValue();

                    assertThat(actualRequest).isEqualToComparingFieldByField(request);
                }
        );
    }

    @Test
    void create_badRequestByProject() throws Exception {
        //given
        request.setProjectId(null);

        var expectedErrorResponse = new ControllerExceptionHandler.ValidationErrorResult("projectId", "must not be null");

        String expectedResponseBody = objectMapper.writeValueAsString(expectedErrorResponse);

        //when
        var result =  mockMvc.perform(
                post("/api/v1/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))

        ).andExpect(status().isBadRequest()).andReturn();

        //then
        String actualResponseBody = result.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }
}