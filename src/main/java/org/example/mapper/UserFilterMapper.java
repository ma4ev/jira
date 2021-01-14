package org.example.mapper;

import org.example.dto.UserFilterRequest;
import org.example.model.UserFilter;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserFilterMapper {

    public abstract UserFilter toDomain(UserFilterRequest request);
}
