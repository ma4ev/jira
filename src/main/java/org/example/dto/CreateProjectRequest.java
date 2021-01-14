package org.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateProjectRequest {

    @NotBlank
    private String name;

    private String description;
}
