package com.sun.web.restful_web_service;

import com.sun.web.restful_web_service.user.UserNotFoundException;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);        
        problem.setTitle("User not found");
        problem.setDetail(exception.getMessage());
        problem.setProperty("timestamp", Instant.now().toString());
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleInvalidArgument(IllegalArgumentException exception) {
          ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
          problem.setTitle("IllegalArgumentException - Invalid request");
          problem.setDetail(exception.getMessage());    
          problem.setProperty("timestamp", Instant.now().toString());
          return problem;
      }

     @ExceptionHandler(Exception.class)
     public ProblemDetail handleUnexpectedException(Exception exception) {
          ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
          problem.setTitle("handleUnexpectedException - Internal server error");
          problem.setDetail("An unexpected error occurred");
          problem.setProperty("timestamp", Instant.now().toString());
          return problem;
      }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request");
        problem.setDetail(
                "Invalid value for parameter '" + exception.getName() + "'");
        problem.setProperty("timestamp", Instant.now().toString());        
        return problem;
    }

     @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
      ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
      problem.setTitle("Validation failed");
      problem.setDetail("One or more fields are invalid");
      problem.setProperty("errors",
              exception.getBindingResult().getFieldErrors().stream()
                      .map(error -> error.getField() + ": " + error.getDefaultMessage())
                      .toList());                                                
      problem.setProperty("timestamp", Instant.now().toString());
      problem.setProperty("errorCount", exception.getBindingResult().getErrorCount());
      return problem;
  }

}
