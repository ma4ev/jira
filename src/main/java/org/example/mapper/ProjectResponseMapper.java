package org.example.mapper;

import org.example.transport.dto.ProjectResponse;
import org.example.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class ProjectResponseMapper {

    public abstract ProjectResponse toDomain(Project project);

    public abstract List<ProjectResponse> toDomain(List<Project> projects);
}
