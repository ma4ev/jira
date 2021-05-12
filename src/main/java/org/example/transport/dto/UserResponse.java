package org.example.transport.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String tagName;
    private String email;
    private String phone;
    private String telegram;
    private Set<Long> projectIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
