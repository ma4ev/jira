package org.example.mapper;

import org.example.dto.CreateUserRequest;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class UserMapper {

    @Mapping(target = "tagName", expression = "java(getTagName(request))")
    public abstract User toDomain(CreateUserRequest request);

    public abstract List<User> toDomain(List<CreateUserRequest> requestList);

    protected String getTagName(CreateUserRequest request){
        return "@" + request.getFirstName().substring(0,1) + "." +  request.getLastName();
    }
}
