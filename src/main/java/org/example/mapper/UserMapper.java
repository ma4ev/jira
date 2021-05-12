package org.example.mapper;

import org.example.entity.User;
import org.example.transport.dto.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class UserMapper {

    @Mapping(target = "tagName", expression = "java(getTagName(request))")
    public abstract User toDomain(CreateUserRequest request);

    protected String getTagName(CreateUserRequest request){
        return "@" + request.getFirstName().substring(0,1) + "." +  request.getLastName();
    }
}
