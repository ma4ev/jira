package org.example.exception;

public class UserNotFoundException extends RuntimeException {

    private final static String msg = "User with id %d not found";

    public UserNotFoundException(Long id) {
        super(String.format(msg, id));
    }
}
