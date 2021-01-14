package org.example.exception;

public class ProjectNotFoundException extends RuntimeException {

    private final static String msg = "Project with id %d not found";

    public ProjectNotFoundException(Long id) {
        super(String.format(msg, id));
    }
}
