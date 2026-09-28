package com.sun.web.restful_web_service.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("User with id " + id + " was not found");
    }
}
