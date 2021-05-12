package org.example.mapper;

import org.example.transport.dto.UserResponse;
import org.example.entity.Project;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class UserResponseMapper {

    @Mapping(target = "projectIds", expression = "java(getProjectIds(user.getProjects()))")
    public abstract UserResponse toDomain(User user);

    public abstract List<UserResponse> toDomain(List<User> users);

    protected Set<Long> getProjectIds(Set<Project> projects){
        return projects.stream()
                .map(Project::getId)
                .collect(Collectors.toSet());
    }
}
