package org.example.controller.v1;

import lombok.RequiredArgsConstructor;
import org.example.mapper.UserFilterMapper;
import org.example.mapper.UserMapper;
import org.example.mapper.UserResponseMapper;
import org.example.model.UserFilter;
import org.example.service.UserService;
import org.example.transport.dto.CreateUserRequest;
import org.example.transport.dto.UserFilterRequest;
import org.example.transport.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        var createdUser = userMapper.toDomain(request);

        return userResponseMapper.toDomain(userService.save(createdUser));
    }

    @PostMapping("/getFiltered")
    public List<UserResponse> getFiltered(@RequestBody UserFilterRequest filterRequest) {
        UserFilter filter = userFilterMapper.toDomain(filterRequest);

        return userResponseMapper.toDomain(userService.get(filter));
    }
}
