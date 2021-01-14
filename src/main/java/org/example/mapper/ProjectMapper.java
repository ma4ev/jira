package org.example.mapper;

import org.example.dto.CreateProjectRequest;
import org.example.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class ProjectMapper {

    public abstract Project toDomain(CreateProjectRequest request);

    public abstract List<Project> toDomain(List<CreateProjectRequest> requests);
}
