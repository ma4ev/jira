package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.model.issue.IssueRelationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "issue_relations")
@SequenceGenerator(name = "default_gen", sequenceName = "issue_relations_id_seq", allocationSize = 1)
public class IssueRelation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    private IssueRelationType relationType;

    @Override
    public boolean checkClass(Object o) {
        return o instanceof IssueRelation;
    }
}
