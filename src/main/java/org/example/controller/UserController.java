package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateUserRequest;
import org.example.dto.UserFilterRequest;
import org.example.dto.UserResponse;
import org.example.entity.User;
import org.example.mapper.UserFilterMapper;
import org.example.mapper.UserMapper;
import org.example.mapper.UserResponseMapper;
import org.example.model.UserFilter;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    private final UserResponseMapper userResponseMapper;

    private final UserMapper userMapper;

    private final UserFilterMapper userFilterMapper;

    @GetMapping
    public List<UserResponse> getAll() {
        return userResponseMapper.toDomain(userService.getAll());
    }

    @PutMapping
    public List<UserResponse> create(@Valid @RequestBody List<CreateUserRequest> requests) {
        List<User> users = userMapper.toDomain(requests);

        return userResponseMapper.toDomain(userService.save(users));
    }

    @PostMapping("/getFiltered")
    public List<UserResponse> getFiltered(@RequestBody UserFilterRequest filterRequest){
        UserFilter filter = userFilterMapper.toDomain(filterRequest);

        return userResponseMapper.toDomain(userService.get(filter));
    }
}
