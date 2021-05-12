package org.example.transport.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.issue.IssuePriority;
import org.example.model.issue.IssueStatus;
import org.example.model.issue.IssueType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateIssueRequest {

    @NotNull
    private Long projectId;

    @NotNull
    private IssueType type;

    @NotBlank
    private String theme;

    @NotNull
    private Long authorId;

    @NotBlank
    private String component;

    private String description;

    @NotNull
    private IssuePriority priority;

    private Long initialAssessment;

    private Long remainingTime;

    private LocalDate datePerformance;

    @NotNull
    private IssueStatus status;
}
