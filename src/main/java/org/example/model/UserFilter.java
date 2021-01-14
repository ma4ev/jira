package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserFilter {

    private List<Long> ids;

    private Long projectId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String tagName;

    private String email;

    private String phone;

    private String telegram;
}
