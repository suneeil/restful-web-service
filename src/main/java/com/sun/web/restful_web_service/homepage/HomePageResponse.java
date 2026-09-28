package com.sun.web.restful_web_service.homepage;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * This is a Java Bean class that represents the response for the home page of a RESTful web service. It contains a message and a status code, which is set to HTTP 200 OK by default. The class uses Lombok annotations to generate getters, setters, and a required arguments constructor.    
 * HomePageResponse
 */
@Getter 
@Setter
@RequiredArgsConstructor 
public class HomePageResponse {

    private final String message;
    private final int statusCode = HttpStatus.OK.value();

    @Override
    public String toString() {
        return "HomePageResponse [message=" + message + ", statusCode=" + statusCode + "]";
    }
}
