package org.example.warshipmanagement.exception;


import org.example.warshipmanagement.domain.Result;
import org.example.warshipmanagement.domain.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<User> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage().length() > 0 ? e.getMessage() : "Unkown error!");
    }
}
