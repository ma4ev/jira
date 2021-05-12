package org.example.mapper;

import org.example.entity.Issue;
import org.example.transport.dto.IssueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class IssueResponseMapper {

    @Mapping(target = "issueId", source = "id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "authorId", source = "author.id")
    public abstract IssueResponse toDomain(Issue issue);

    public abstract List<IssueResponse> toDomain(List<Issue> issues);
}
