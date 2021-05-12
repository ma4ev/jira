package org.example.transport.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.issue.IssuePriority;
import org.example.model.issue.IssueStatus;
import org.example.model.issue.IssueType;

import java.time.LocalDate;

@Getter
@Setter
public class IssueResponse {

    private Long issueId;

    private Long projectId;

    private IssueType type;

    private String theme;

    private Long authorId;

    private String component;

    private String description;

    private IssuePriority priority;

    private Long initialAssessment;

    private Long remainingTime;

    private LocalDate datePerformance;

    private IssueStatus status;
}
