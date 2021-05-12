package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.model.issue.IssuePriority;
import org.example.model.issue.IssueStatus;
import org.example.model.issue.IssueType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "issues")
@SequenceGenerator(name = "default_gen", sequenceName = "issues_id_seq", allocationSize = 1)
public class Issue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private IssueType type;

    @Column(name = "theme", nullable = false)
    private String theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "component", nullable = false)
    private String component;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private IssuePriority priority;

    @Column(name = "initial_assessment")
    private Long initialAssessment;

    @Column(name = "remaining_time")
    private Long remainingTime;

    //@TODO need create file service , create entity Attach and relates to Issue

    @Column(name = "date_performance")
    private LocalDate datePerformance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IssueStatus status;

    @Override
    public boolean checkClass(Object o) {
        return o instanceof Issue;
    }
}
