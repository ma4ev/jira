package org.example.exception;

public class IssueNotFoundException extends ResourceNotFoundException {

    private final static String msg = "Issue with id %d not found";

    public IssueNotFoundException(Long id) {
        super(String.format(msg, id));
    }
}
